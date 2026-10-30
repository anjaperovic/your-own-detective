(ns your-own-detective.core
  (:gen-class))

(defn -main 
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))

(def examples
  {1 [{:suspect "Marc"
      :evidence-type :alibi
      :description "Marc says he has been home at the time of murder."}]

   2 [{:suspect "Anna"
       :evidence-type :motive
       :description "Anna had a fight with the victim."}
      
      {:suspect "Marc"
       :evidence-type :contradiction
       :description "Marc's brother said he wasn't home."}]

   4 [{:suspect "Ellen"
      :evidence-type :contradiction
      :description "A love letter addressed to the victim was found with her signature on it. "}]
   5 [{
       :suspect "Peter"
       :evidence-type :alibi
       :description "Peter was working the night of the murder."
   }
      
   ]})


;; vadi sve dokaze za odredjeno poglavlje
(get examples 1)
(get examples 2)
(defn get-evidence-by-chapter [n](get examples n))
(get-evidence-by-chapter 3)
(get-evidence-by-chapter 2)
;;radi


(filter #(=(:evidence-type %) :contradiction) examples) ;;prazno

(def values-examples(vals examples))
(filter #(= (:evidence-type %) :contradiction) values-examples)
;;nije se uhvatio nijedan

(def values-examples-2(apply concat values-examples))
values-examples-2
;;svi 
;;vadi sve dokaze za odredjeni tip
(defn get-evidence-by-type [type]
  (filter #(= (:evidence-type %) type) values-examples-2)) 

(get-evidence-by-type :contradiction )
;;radi








