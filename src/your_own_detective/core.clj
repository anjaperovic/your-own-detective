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
(defn get-evidence-by-chapter [n db](get db n))
(get-evidence-by-chapter 3 examples)
(get-evidence-by-chapter 2 examples)
;;radi


;; vadi sve dokaze do odredjenog poglavlja
(defn get-evidence-up-to-chapter [n db] (loop [iteration 1 final-evidence []]
                                          (if (> iteration n)
                                            final-evidence
                                            (recur (inc iteration)
                                                  (into final-evidence (get-evidence-by-chapter iteration db))
                                                       ))))
(get-evidence-up-to-chapter 5 examples) 
(get-evidence-up-to-chapter 3 examples)
(get-evidence-up-to-chapter 10 examples)
;;radi



(filter #(=(:evidence-type %) :contradiction) examples) ;;prazno

(def values-examples-vec(vals examples))
values-examples-vec
(filter #(= (:evidence-type %) :contradiction) values-examples-vec)
;;nije se uhvatio nijedan

(def values-examples(apply concat values-examples-vec))
values-examples
;;svi 

;;vadi sve dokaze za odredjeni tip
(defn get-evidence-by-type [type db]
  (filter #(= (:evidence-type %) type) (apply concat (vals db)))) 

(get-evidence-by-type :contradiction examples)
;;radi








