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
	(slot resultado_primera_etapa)
	(slot resultado_espondilitis(type SYMBOL)
		(allowed-values
			nil
			espondilitis_nula
			artritis_psoriasica 
			colitis_ulcerosa
			espodilo_artritis_juvenil
			espondilo_artritis_indiferenciada)
		(default nil))
	(slot grado_de_confianza(type INTEGER)
		(default 0))
)

(deftemplate dolor
	(slot id_diagnostico)
	(slot zona(type SYMBOL)
		(allowed-values lumbar dorsal cervical))
	(slot inicio_dolor)
	(slot condicion_alivio)
	(slot recurrencia(type INTEGER))
	(slot tipo)
)

(deftemplate antecedente_familiar
	(slot id_paciente
		(type INTEGER))
	(slot parentesco)
	(slot enfermedad)
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
			entesitis))
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
		(allowed-values HLAB27 placas resonancia))
)
;-------------------------------------------------------------------------------------------------------
; Definicion de reglas
;-------------------------------------------------------------------------------------------------------
;SECCION 1

(defrule mayor_edad_limite
	(paciente(edad ?x)(id_paciente ?id))
	?diag<- (diagnostico (id_paciente ?id)(resultado_primera_etapa nil))
=>(if (> ?x 40)
then(modify ?diag (resultado_primera_etapa "Problema degenerativo"))
else(modify ?diag (resultado_primera_etapa "seccion 2"))))

;SECCION 2
(defrule lumbar_mecanico
	?dolor<-(dolor(id_diagnostico ?id)(tipo nil)(zona lumbar)(inicio_dolor "ejercicio")(condicion_alivio "reposo"))
	(diagnostico(id_diagnostico ?id)(resultado_primera_etapa "seccion 2"))
=>(modify ?dolor (tipo "lumbar mecanico")))

(defrule lumbar_inflamatorio
	?dolor<-(dolor(id_diagnostico ?id)(tipo nil)(zona lumbar)(inicio_dolor "reposo")(condicion_alivio "ejercicio"))
	?diag<-(diagnostico(id_diagnostico ?id)(resultado_primera_etapa "seccion 2"))
=>(modify ?dolor (tipo "lumbar inflamatorio"))
	(modify ?diag (resultado_primera_etapa "seccion 2.5")))

(defrule compromiso_axial
	?diag<-(diagnostico(id_diagnostico ?id)(resultado_primera_etapa "seccion 2.5"))
	(dolor(tipo "lumbar inflamatorio")(recurrencia ?rec)(id_diagnostico ?id))
=>(if (> ?rec 3) 
then (modify ?diag (resultado_primera_etapa "compromiso axial"))
else (modify ?diag (resultado_primera_etapa "reprogramar consulta"))))

;ESTUDIOS
(defrule HLAB27_test
	?diag<-(diagnostico(id_diagnostico ?id)(resultado_primera_etapa "compromiso axial")(resultado_espondilitis nil)(grado_de_confianza ?gdc))
	(estudio(tipo_estudio HLAB27)(resultado ?res))
=>(if (= 0 (str-compare ?res si))
then(modify ?diag (resultado_espondilitis espondilo_artritis_indiferenciada)(resultado_primera_etapa "espera_placas")(grado_de_confianza (+ ?gdc 30)))
else(modify ?diag (resultado_primera_etapa "espera_placas"))))

(defrule placas_test
	?diag<-(diagnostico(id_diagnostico ?id)(resultado_primera_etapa "espera_placas")(grado_de_confianza ?gdc))
	(estudio(tipo_estudio placas)(resultado ?res))
=>(if (= 0 (str-compare ?res si))
then(modify ?diag (resultado_primera_etapa "categorizar espondilitis")(resultado_espondilitis espondilo_artritis_indiferenciada)(grado_de_confianza 100))
else(modify ?diag (resultado_primera_etapa "placas no concluyentes"))))

(defrule resonancia_test
	?diag<-(diagnostico(id_diagnostico ?id)(resultado_primera_etapa "placas no concluyentes")(grado_de_confianza ?gdc))
	(estudio(tipo_estudio resonancia)(resultado ?res))
=>(if (= 0 (str-compare ?res si))
then(modify ?diag (resultado_espondilitis espondilo_artritis_indiferenciada)(grado_de_confianza 100)(resultado_primera_etapa "categorizar espondilitis"))
else(modify ?diag (resultado_primera_etapa "derivar a reumatologo")(resultado_espondilitis espondilitis_nula)(grado_de_confianza 0))))

;CATEGORIZACION
(defrule esp_juvenil
	?diag<-(diagnostico(resultado_primera_etapa "categorizar espondilitis")(id_paciente ?id))
	(paciente(id_paciente ?id)(edad ?edad))
	(test (< ?edad 16))
=>(modify ?diag (resultado_espondilitis espodilo_artritis_juvenil)(resultado_primera_etapa "diagnostico final")))

(defrule art_psoriasica
	?diag<-(diagnostico(resultado_primera_etapa "categorizar espondilitis")(id_paciente ?id))
	(paciente(id_paciente ?id))
	(enfermedades_preexistentes(enfermedad psoriasis))
=>(modify ?diag (resultado_espondilitis artritis_psoriasica)(resultado_primera_etapa "diagnostico final")))

(defrule col_ulcerosa
	?diag<-(diagnostico(resultado_primera_etapa "categorizar espondilitis")(id_paciente ?id))
	(paciente(id_paciente ?id))
	(enfermedades_preexistentes(enfermedad diarrea))
=>(modify ?diag (resultado_espondilitis colitis_ulcerosa)(resultado_primera_etapa "diagnostico final")))


;-------------------------------------------------------------------------------------------------------
; Facts de prueba 
;-------------------------------------------------------------------------------------------------------

 ;(deffacts init_pacientes(paciente(id_paciente 1)(nombre "rafael")(edad 25)))
 ;(deffacts init_diagnosticos(diagnostico(id_paciente 1)(id_diagnostico 1)))
 ;(deffacts init_dolor(dolor(zona lumbar)(inicio_dolor "reposo")(condicion_alivio "ejercicio")(id_diagnostico 1)(recurrencia 5)))
 ;(deffacts init_estudios(estudio(tipo_estudio HLAB27)(resultado no))
 ;	(estudio(tipo_estudio placas)(resultado si))
 ;	(estudio(tipo_estudio resonancia)(resultado no)))
 ;(deffacts init_enfermedades(enfermedades_preexistentes(enfermedad diarrea psoriasis)))
