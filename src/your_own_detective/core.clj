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


(def start-of-book ()) ;napravi da pocinje od pocetka bez onog uvodnog dela i sadrzaja

(def text (slurp "https://www.gutenberg.org/cache/epub/69087/pg69087.txt"))
(count text)
(subs text 0 500)

(def client (java.net.http.HttpClient/newHttpClient))
(def uri (java.net.URI/create "http://localhost:11434/api/chat"))

(def prompt (str "You are an expert in searching for clues and evidence in thriller books."
                 "I will give you a chapter of a book and you should analyze it carefully and look for evidence"
                 "You should return the data in a specific format (use the following examples)"
                 "Example 1:"
                 "{
                     :chapter 1                      
                     :suspect "" Marc ""                 
                     :evidence-type :alibi   
                     :description "" Marc said he was reading at home at the time of the murder"" 
                     :confidence-score 0.85    
                  }"
                 "Example 2:"
                 "{
                      :chapter 2                      
                      :suspect "" Anna ""                 
                      :evidence-type :motive   
                      :description " "Anna had a fight with the victim."" 
                      :confidence-score 1    
                  }"
                 

                 "Example 3:"
                  "{
                      :chapter 5                      
                      :suspect "" Marc ""                 
                      :evidence-type :contradiction   
                      :description "" Marc's brother said he wasn't home at the time of murder."" 
                      :confidence-score 0.9    
                  }"
                 "Example 4:"
                 "{
                      :chapter 9                      
                      :suspect " " Tina " "                 
                      :evidence-type :forensic   
                      :description " " Tina's fingerprints were found in the victim's car" " 
                      :confidence-score 0.95    
                                   }"
                 
                 "Example 5:"
                 "{
                      :chapter 25                      
                      :suspect "" Marc ""                 
                      :evidence-type :behaviour   
                      :description "" Marc was feeling extremly nervous when talking to the police, he was shaking and stuttering."" 
                      :confidence-score 0.55    
                                   }"
                "Example 6:"
                "{
                                      :chapter 25                      
                                      :suspect "" Tina ""                 
                                      :evidence-type  :means   
                                      :description  ""The murder weapon was found in Tinas car" " 
                                      :confidence-score 0.9  
                                                   }"
                                   
                  "Evidence types are: alibi, motive, contradiction, forensic, means, behaviour. If you think you can classify some evidence into another group that makes sense, add it to the list."
                  "If you are not sure how to classify evidence, the evidence type should then be ""other""."
                  "Here's the text:" (subs text 0 10000) ))
(def request-body
  (str "{\"model\": \"gemma3:latest\", \"stream\": false, \"messages\": [{\"role\": \"user\", \"content\": \""
       (-> prompt
           (clojure.string/replace "\\" "\\\\")
           (clojure.string/replace "\"" "\\\"")
           (clojure.string/replace "\n" " ")
           (clojure.string/replace "\r" ""))
       "\"}]}"))
(def my-requst
  (-> (java.net.http.HttpRequest/newBuilder)
      (.uri uri)
      (.header "Content-Type" "application/json")
      (.POST (java.net.http.HttpRequest$BodyPublishers/ofString request-body))
      (.build)))

(def response
  (.send client my-requst (java.net.http.HttpResponse$BodyHandlers/ofString)))

(.body response)
;; u postanalzi u clojuru vecu tezinu daj stvarima tipa forensic a manju za behaviour na primer