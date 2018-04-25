;-------------------------------------------------------------------------------------------------------
; Definicion de templates
;-------------------------------------------------------------------------------------------------------
(deftemplate auto 
	(slot marca (type STRING)) 
	(slot modelo (type STRING))
	(slot año_patentamiento (type NUMBER) (range 1900 2100))
	(slot uso (type SYMBOL) (allowed-symbols Profesional Particular)) 
	(slot radicado (type SYMBOL) (allowed-symbols Buenos_Aires Chubut Capital_Federal Santa_Fe Cordoba)) 
	(slot patente (type STRING))) 
(deftemplate impuesto_automotor_provincial 
	(slot patente (type STRING)) 
	(slot Paga (type SYMBOL) (allowed-symbols SI NO))) 

;-------------------------------------------------------------------------------------------------------
; Definicion de reglas
;-------------------------------------------------------------------------------------------------------
(defrule no_paga_10(auto (patente ?y)(año_patentamiento ?x)(radicado ~Chubut))(test (< 10 (- 2017 ?x)))
=>(assert (impuesto_automotor_provincial (patente ?y)(Paga NO))))

(defrule no_paga_15 (auto (patente ?y)(año_patentamiento ?x)(radicado Chubut))(test (< 15 (- 2017 ?x)))
=> (assert (impuesto_automotor_provincial (patente ?y)(Paga NO))))

;-------------------------------------------------------------------------------------------------------
; Definicion de templates
;-------------------------------------------------------------------------------------------------------
(deffacts init(auto(marca "ford")(modelo "sierra")(año_patentamiento 1987)))



