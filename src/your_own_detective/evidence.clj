(ns your-own-detective.evidence
  (:require [cheshire.core :refer :all]))

(def examples
  [{:chapter 1
    :suspect "Marc"
    :evidence-type :alibi
    :description "Marc says he has been home at the time of murder."
    :confidence-score 0.7}

   {:chapter 2
    :suspect "Anna"
    :evidence-type :motive
    :description "Anna had a fight with the victim."
    :confidence-score 0.6}

   {:chapter 2
    :suspect "Marc"
    :evidence-type :contradiction
    :description "Marc's brother said he wasn't home."
    :confidence-score 0.8}

   {:chapter 4
    :suspect "Ellen"
    :evidence-type :contradiction
    :description "A love letter addressed to the victim was found with her signature on it. "
    :confidence-score 0.55 }
   
   {:chapter 5
    :suspect "Peter"
    :evidence-type :alibi
    :description "Peter was working the night of the murder."
    :confidence-score 0.89}])


;; vadi sve dokaze za odredjeno poglavlje


(defn get-evidence-by-chapter [n db] (filter #(= n (:chapter %)) db) )

(get-evidence-by-chapter 6  examples)


;; vadi sve dokaze do odredjenog poglavlja

(defn get-evidence-up-to-chapter [n db] (filter #(<= (:chapter %) n) db))
(get-evidence-up-to-chapter 5 examples)
(get-evidence-up-to-chapter 3 examples)
(get-evidence-up-to-chapter 0 examples)


;;vadi sve dokaze za odredjeni tip
(defn get-evidence-by-type [type db]
  (filter #(= (:evidence-type %) type) db))

;sve dokaze za odredjenog lika

(defn get-evidence-by-suspect [suspect db]
  (filter #(= (:suspect %) suspect) db))
(get-evidence-by-suspect "Marc" examples)

(def start-of-book ()) ;napravi da pocinje od pocetka bez onog uvodnog dela i sadrzaja
(def separate-chapters())
;funkcija za pretvaranje jednog jsona u mapu + dodavanje chaptera u tu mapu (znaci prima output za odredjeno poglavlje i broj poglavlja)
; izvalcenje svakog objekta pa nad njim fja parse-string + i tip dokaza mora da bude keyword

;; parse some json and get keywords back

(defn json-to-map [db]
 (parse-string (generate-string db) true) )
(json-to-map examples)


; deljenje predugackog poglavja
; racunanje broja tokena poglavlja
;funkcija koja prolazi kroz sve dokaze i trazi dva koja su kontradikotrna pa tom drugom dodeli tip contradiction a prvom smanji tezinu
; cuvanje dokaza
; citanje dokaza
; racunjanje tezine dokaza
; izracunavanje verovatnoce da je osumnjiceni i pocinitelj
; fja koja smanjuje tezinu dokaza (odvojeno?)
; najuticajniji dokazi koji govore u prilog da je osumnjiceni i pocinitelj

;probaj sa vecim modelom
;napisi readme (user storiji, uputstvo za knjigu)