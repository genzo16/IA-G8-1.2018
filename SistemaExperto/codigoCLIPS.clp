;-------------------------------------------------------------------------------------------------------
; Sistema experto: Diagnostico de Espondiloartritis (EsA)
; IA 1°Cuat.2018 Grupo 8
;-------------------------------------------------------------------------------------------------------
(defmodule MAIN (export ?ALL))

;-------------------------------------------------------------------------------------------------------
; Definicion de templates
;-------------------------------------------------------------------------------------------------------
(deftemplate paciente
	(slot id_paciente 
		(type INTEGER))
	(slot nombre)
	(slot apellido)
	(slot sexo 
		(type SYMBOL)
		(allowed-values masculino femenino))
	(slot edad)
)

(deftemplate diagnostico
	(slot id_paciente)
	(slot id_diagnostico)
	(slot fecha)
	(slot derivacion (default null))
	(slot estudio_solicitado (default ninguno))
	(slot dolor_lumbar_inflamatorio (default inexistente))
	(slot resultado (default null))
	(slot resultado_espondilitis(type SYMBOL)
		(allowed-values null
						espondilitis_nula
						artritis_psoriasica 
						artritis_reactiva
						colitis_ulcerosa
						espodilo_artritis_juvenil
						espondilo_artritis_indiferenciada)
		(default null))
	(slot grado_de_confianza(type INTEGER)
		(default 0))
)

(deftemplate dolor
	(slot id_diagnostico)
	(slot zona(type SYMBOL)
		(allowed-values null
						lumbar 
						dorsal 
						cervical
						articular
						intestinal
						ocular
						enrojecimiento_manchas)
		(default null))
	(slot inicio_dolor(type SYMBOL)
		(allowed-values null
						peso
						ejercicio
						postura
						nocturno)
		(default null))
	(slot condicion_alivio(type SYMBOL)
		(allowed-values null
						reposo
						actividad
						anti_inflamatorios)
		(default null))
	(slot recurrencia(type INTEGER))
	(slot tipo
		(default null))
)

(deftemplate antecedente_familiar
	(slot id_paciente
		(type INTEGER))
	(slot parentesco)
	(multislot enfermedad
		(type SYMBOL)
		(allowed-values 
			uveitis
			dactilitis
			rotuliano
			diarrea
			psoriasis
			entesitis
			infeccion)
			)
)

(deftemplate enfermedades_preexistentes
	(multislot enfermedad
		(type SYMBOL)
		(allowed-values 
			uveitis
			dactilitis
			rotuliano
			diarrea
			psoriasis
			entesitis
			infeccion)
			)
)

(deftemplate laboratorio
	(slot id_laboratorio
		(type INTEGER))
	(slot ERS
		(type FLOAT))
	(slot PCR
		(type FLOAT))
)

(deftemplate estudio
	(slot id_estudio
		(type INTEGER))
	(slot id_paciente
		(type INTEGER))
	(slot resultado
		(type SYMBOL)
		(allowed-values si no))
	(slot tipo_estudio
		(type SYMBOL)
		(allowed-values HLAB27 radiografia resonancia))
)
;-------------------------------------------------------------------------------------------------------
; Definicion de reglas
;-------------------------------------------------------------------------------------------------------

; Clasificacion de dolor
(defrule lumbar_mecanico
	?dolor<-(dolor(id_diagnostico ?id)(tipo null)(zona lumbar | cervical)(inicio_dolor peso | ejercicio | postura)(condicion_alivio reposo))
	(diagnostico(id_diagnostico ?id)(resultado null))
=>(modify ?dolor (tipo "lumbar mecanico")))

(defrule articular
	?dolor<-(dolor(id_diagnostico ?id)(tipo null)(zona articular))
	(enfermedades_preexistentes(enfermedad $?enf))
	?diag<-(diagnostico(id_diagnostico ?id)(resultado null))
=>(bind ?flag 0)
	(foreach  ?e $?enf 
	(if (and (or (eq ?e dactilitis) (eq ?e entesitis))(!= ?flag 1))
		then (modify ?dolor (tipo "articular"))(bind ?flag 1)
	))
	(if (= ?flag 0)
		then (modify ?diag (resultado "derivar")) // reumatologo
	)
)

(defrule intestinal
	?dolor<-(dolor(id_diagnostico ?id)(tipo null)(zona intestinal))
	(enfermedades_preexistentes(enfermedad $?enf))
	?diag<-(diagnostico(id_diagnostico ?id)(resultado null))
=>(bind ?flag 0)
	(foreach  ?e $?enf 
	(if (and (eq ?e diarrea)(!= ?flag 1))
		then (modify ?dolor (tipo "intestinal"))(bind ?flag 1)
	))
	(if (= ?flag 0)
		then (modify ?diag (resultado "derivar"))//gastroenterologo
	)
)

(defrule ocular
	?dolor<-(dolor(id_diagnostico ?id)(tipo null)(zona ocular))
	(enfermedades_preexistentes(enfermedad $?enf))
	?diag<-(diagnostico(id_diagnostico ?id)(resultado null))
=>(bind ?flag 0)
	(foreach  ?e $?enf 
	(if (and (eq ?e uveitis)(!= ?flag 1))
		then (modify ?dolor (tipo "ocular"))(bind ?flag 1)
	))
	(if (= ?flag 0)
		then (modify ?diag (resultado "derivar"))//oculista
	)
)

(defrule enrojecimiento_manchas
	?dolor<-(dolor(id_diagnostico ?id)(tipo null)(zona enrojecimiento_manchas)); manos, rodillas, codos, etc.
	(enfermedades_preexistentes(enfermedad $?enf))
	?diag<-(diagnostico(id_diagnostico ?id)(resultado null))
=>(bind ?flag 0)
	(foreach  ?e $?enf 
	(if (and (eq ?e psoriasis)(!= ?flag 1))
		then (modify ?dolor (tipo "enrojecimiento_manchas"))(bind ?flag 1)
	))
	(if (= ?flag 0)
		then (modify ?diag (resultado "derivar"))//dermatologo
	)
)

; 
;(defrule dolor_comun
;	?dolor<-(dolor(id_diagnostico ?id)(tipo null))
;	?diag<-(diagnostico(id_diagnostico ?id)(resultado null))
;=>(bind ?flag 0)
;	(foreach  ?e $?enf 
;	(if (and (eq ?e uveitis)(!= ?flag 1))
;		then (modify ?dolor (tipo "ocular"))(bind ?flag 1)
;	))
;	(if (= ?flag 0)
;		then (modify ?diag (resultado "derivar"))//oculista
;	)
;)

; DERIVACION
(defrule derivar
	(dolor(id_diagnostico ?id)(zona ?z_dolor))
	?diag<-(diagnostico(id_diagnostico ?id)(resultado "derivar"))
=>(switch ?z_dolor
		(case articular then (bind ?return-value "reumatologo"))
		(case intestinal then (bind ?return-value "gastroenterologo"))
		(case ocular then (bind ?return-value "oculista"))
		(case enrojecimiento_manchas then (bind ?return-value "dermatologo"))
		(default then (bind ?return-value "medico general"))
	)
	(modify ?diag (resultado "derivacion")(derivacion ?return-value))
)

;---------------------------------------------------------------------
(defrule evaluar_antecedentes_articular
	(paciente(edad ?x)(id_paciente ?id))
	(dolor(id_diagnostico ?id)(tipo "articular"))
	(antecedente_familiar(enfermedad $?ant))
	?diag<-(diagnostico(id_diagnostico ?id)(resultado null))
=>(bind ?flag 0)
	(foreach  ?e $?ant 
	(if (and (eq ?e psoriasis)(!= ?flag 1))
		then (modify ?diag (resultado "Artritis psoriasica")(resultado_espondilitis artritis_psoriasica))(bind ?flag 1));Artritis psoriasica
	(if (and (eq ?e infeccion)(!= ?flag 1))
		then (modify ?diag (resultado "Artritis reactiva")(resultado_espondilitis artritis_reactiva))(bind ?flag 1));Artritis reactiva
	)
)

(defrule evaluar_antecedentes_intestinal
	(paciente(edad ?x)(id_paciente ?id))
	(dolor(id_diagnostico ?id)(tipo "intestinal"))
	(antecedente_familiar(enfermedad $?ant))
	?diag<-(diagnostico(id_diagnostico ?id)(resultado null))
=>(foreach  ?e $?ant 
	(if (eq ?e diarrea)
		then (modify ?diag  (resultado "derivar")))
	)
)

(defrule evaluar_antecedentes_ocular
	(paciente(edad ?x)(id_paciente ?id))
	(dolor(id_diagnostico ?id)(tipo "ocular"))
	(antecedente_familiar(enfermedad $?ant))
	?diag<-(diagnostico(id_diagnostico ?id)(resultado null))
=>(bind ?flag 0)
	(foreach  ?e $?ant 
	(if (eq ?e uveitis)
		then (modify ?diag (resultado "realizar estudios")(estudio_solicitado "test HLAB27"))(bind ?flag 1)
	))
	(if (= ?flag 0)
		then (modify ?diag  (resultado "derivar")))
)

(defrule HLAB27_test_A
	?diag<-(diagnostico(id_diagnostico ?id)(resultado "test HLAB27")(resultado_espondilitis null)(grado_de_confianza ?gdc))
	(estudio(tipo_estudio HLAB27)(resultado ?res))
=>(if (= 0 (str-compare ?res si))
then(modify ?diag  (resultado "derivar")(grado_de_confianza (+ ?gdc 30)))); reumatologo
)

(defrule evaluar_antecedentes_psoriasis
	(paciente(edad ?x)(id_paciente ?id))
	(dolor(id_diagnostico ?id)(tipo "enrojecimiento_manchas"))
	(antecedente_familiar(enfermedad $?ant))
	?diag<-(diagnostico(id_diagnostico ?id)(resultado null))
=>(bind ?flag 0)
(foreach  ?e $?ant 
	(if (eq ?e psoriasis)
		then (modify ?diag  (resultado "derivar"))(bind ?flag 1)))// fin  dermatologo
	(if (= ?flag 0)
		then (modify ?diag (resultado "derivar"))) ;"ver medico general -psoriasis")))
)

(defrule mayor_edad_limite_mecanico
	(paciente(edad ?x)(id_paciente ?id))
	(dolor (tipo "lumbar mecanico"))
	?diag<- (diagnostico (id_paciente ?id)(resultado null))
=>(if (> ?x 45)
then(modify ?diag (resultado "Problema degenerativo"));FIN
else(modify ?diag  (resultado "derivar"))));FIN

(defrule lumbar_inflamatorio
	?dolor<-(dolor(id_diagnostico ?id)(tipo null)(zona lumbar)(inicio_dolor nocturno)(condicion_alivio actividad ))
	?diag<-(diagnostico(id_diagnostico ?id)(resultado null))
=>(modify ?dolor (tipo "lumbar inflamatorio"))
	(modify ?diag (resultado "comprobar degenerativo")))

(defrule mayor_edad_limite_inflamatorio
	(paciente(edad ?x)(id_paciente ?id))
	?diag<- (diagnostico (id_paciente ?id)(resultado "comprobar degenerativo"))
=>(if (> ?x 45)
then(modify ?diag (resultado "derivar")); "Consultar medico clinico"));FIN
else(modify ?diag (resultado "comprobar comp axial"))))

(defrule compromiso_axial
	?diag<-(diagnostico(id_diagnostico ?id)(resultado "comprobar comp axial"))
	(dolor(tipo "lumbar inflamatorio")(recurrencia ?rec)(id_diagnostico ?id))
=>(if (> ?rec 3) 
	then (modify ?diag (resultado "compromiso axial"))
	else (modify ?diag (resultado "reprogramar consulta")); fin x recurrencia
   )
)

(defrule antecedentes  
	(paciente(edad ?x)(id_paciente ?id))
	(enfermedades_preexistentes(enfermedad $?ant))
	?diag<- (diagnostico (id_paciente ?id)(resultado "compromiso axial"))
=>(bind ?count 0)
	(foreach  ?e $?ant
      (bind ?count (+ ?count 1))
    )
	(if (< ?count 2)
		then(modify ?diag(resultado "realizar estudios") (estudio_solicitado "radiografia"))
		else(modify ?diag(resultado "realizar estudios") (estudio_solicitado "HLAB27"))
	)
)

(defrule radiografia
	?diag<-(diagnostico(id_diagnostico ?id)(resultado "realizar radiografia"))
	(estudio (tipo_estudio radiografia)(resultado ?res))
=>(if (= 0 (str-compare ?res si)) 
	then (modify ?diag (resultado "categorizar espondilitis"));rx sacroilitis
	else (modify ?diag (resultado "realizar estudios") (estudio_solicitado "resonancia")));rx normal
)

(defrule lab_HLAB27
	?diag<-(diagnostico(id_diagnostico ?id)(resultado "analisis lab HLAB27"))
	(estudio (tipo_estudio HLAB27)(resultado ?res))
	(laboratorio(ERS ?ers)(PCR ?pcr))
=>(if (= 0 (str-compare ?res si)) 
	then (if (and (> ?ers 20.0)(> ?pcr 0.5))
		then (modify ?diag (resultado "realizar radiografia"))
		else (modify ?diag (resultado "no hay evidencia suficiente")));no definido
	else (if (and (> ?ers 20.0)(> ?pcr 0.5))
		then (modify ?diag (resultado "realizar estudios") (estudio_solicitado "radiografia"))
		else (modify ?diag (resultado "realizar estudios") (estudio_solicitado "radiografia")))
))

;ESTUDIOS
(defrule HLAB27_test
	?diag<-(diagnostico(id_diagnostico ?id)(resultado "compromiso axial")(resultado_espondilitis null)(grado_de_confianza ?gdc))
	(estudio(tipo_estudio HLAB27)(resultado ?res))
=>(if (= 0 (str-compare ?res si))
then(modify ?diag (resultado_espondilitis espondilo_artritis_indiferenciada)(resultado "espera_radiografia")(grado_de_confianza (+ ?gdc 30)))
else(modify ?diag (resultado "espera_radiografia"))))

(defrule radiografia_test
	?diag<-(diagnostico(id_diagnostico ?id)(resultado "espera_radiografia")(grado_de_confianza ?gdc))
	(estudio(tipo_estudio radiografia)(resultado ?res))
=>(if (= 0 (str-compare ?res si))
then(modify ?diag (resultado "categorizar espondilitis")(resultado_espondilitis espondilo_artritis_indiferenciada)(grado_de_confianza 100))
else(modify ?diag (resultado "radiografia no concluyentes"))))

(defrule resonancia_test
	?diag<-(diagnostico(id_diagnostico ?id)(resultado "radiografia no concluyentes")(grado_de_confianza ?gdc))
	(estudio(tipo_estudio resonancia)(resultado ?res))
=>(if (= 0 (str-compare ?res si))
then(modify ?diag (resultado_espondilitis espondilo_artritis_indiferenciada)(grado_de_confianza 100)(resultado "categorizar espondilitis"))
else(modify ?diag (resultado "derivar")(resultado_espondilitis espondilitis_nula)(grado_de_confianza 0))))

;CATEGORIZACION
(defrule esp_juvenil
	?diag<-(diagnostico(resultado "categorizar espondilitis")(id_paciente ?id))
	(paciente(id_paciente ?id)(edad ?edad))
	(test (< ?edad 16))
=>(modify ?diag (resultado_espondilitis espodilo_artritis_juvenil)(resultado "diagnostico final")))

(defrule art_psoriasica
	?diag<-(diagnostico(resultado "categorizar espondilitis")(id_paciente ?id))
	(paciente(id_paciente ?id))
	(enfermedades_preexistentes(enfermedad psoriasis))
=>(modify ?diag (resultado_espondilitis artritis_psoriasica)(resultado "diagnostico final")))

(defrule col_ulcerosa
	?diag<-(diagnostico(resultado "categorizar espondilitis")(id_paciente ?id))
	(paciente(id_paciente ?id))
	(enfermedades_preexistentes(enfermedad diarrea))
=>(modify ?diag (resultado_espondilitis colitis_ulcerosa)(resultado "diagnostico final")))


;-------------------------------------------------------------------------------------------------------
; Facts de prueba 
;-------------------------------------------------------------------------------------------------------
; todos los facts tienen que existir aunque esten vacios
;(deffacts init_pacientes(paciente(id_paciente 1)(nombre "rafael")(edad 32)))
;(deffacts init_diagnosticos(diagnostico(id_paciente 1)(id_diagnostico 1)))
;(deffacts init_dolor(dolor(id_diagnostico 1)(zona ocular)(inicio_dolor nocturno)(condicion_alivio actividad)(recurrencia 5)))
;(deffacts init_estudios(estudio(tipo_estudio HLAB27)(resultado si))
;	(estudio(tipo_estudio radiografia)(resultado si))
;	(estudio(tipo_estudio resonancia)(resultado no)))
;(deffacts init_enfermedades(enfermedades_preexistentes(enfermedad uveitis )));dactilitis entesitis
;(deffacts init_laboratorio(laboratorio(PCR 0.6)(ERS 21.0)))
;(deffacts init_antecedentes_fam(antecedente_familiar(enfermedad )))

