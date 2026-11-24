(ns your-own-detective.core-test
  (:require [clojure.test :refer :all]
            [your-own-detective.evidence :refer :all]
            [midje.sweet :refer :all]))


(fact  "proba" (+ 1 1) => 2)

(fact "get-evidence-by-chapter returns all evidence in a certain chapter"
      (get-evidence-by-chapter 2 examples) => contains [{:chapter 2
                                                         :suspect "Anna"
                                                         :evidence-type :motive
                                                         :description "Anna had a fight with the victim."
                                                         :confidence-score 0.6}
                                                        
                                                        {:chapter 2
                                                         :suspect "Marc"
                                                         :evidence-type :contradiction
                                                         :description "Marc's brother said he wasn't home."
                                                         :confidence-score 0.8}])

(fact "get-evidence-up-to-chapter returns all evidence in a certain chapter and in the chapters preceding it"
      (get-evidence-up-to-chapter 5 examples) => (contains [{:chapter 1
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
                                                             :confidence-score 0.55}
                                                            
                                                            {:chapter 5
                                                             :suspect "Peter"
                                                             :evidence-type :alibi
                                                             :description "Peter was working the night of the murder."
                                                             :confidence-score 0.89}]))

(fact "get-evidence-by-type returns all evidence with a given type"
      (get-evidence-by-type :alibi examples) => (contains [{:chapter 1
                                                            :suspect "Marc"
                                                            :evidence-type :alibi
                                                            :description "Marc says he has been home at the time of murder."
                                                            :confidence-score 0.7}
                                                           {:chapter 5
                                                            :suspect "Peter"
                                                            :evidence-type :alibi
                                                            :description "Peter was working the night of the murder."
                                                            :confidence-score 0.89}]))

(fact "get-evidence-by-suspect returns all evidence concerning the given suspect"
      (get-evidence-by-suspect "Marc" examples)=> (contains [{:chapter 1
                                                              :suspect "Marc"
                                                              :evidence-type :alibi
                                                              :description "Marc says he has been home at the time of murder."
                                                              :confidence-score 0.7}
                                                              {:chapter 2
                                                              :suspect "Marc"
                                                              :evidence-type :contradiction
                                                              :description "Marc's brother said he wasn't home."
                                                              :confidence-score 0.8}]))
(fact "get-evidence-by-suspect returns all evidence concerning the given suspect"
      (get-evidence-by-suspect "Anna" examples) => (contains [{:chapter 1
                                                               :suspect "Marc"
                                                               :evidence-type :alibi
                                                               :description "Marc says he has been home at the time of murder."
                                                               :confidence-score 0.7}
                                                              {:chapter 2
                                                               :suspect "Marc"
                                                               :evidence-type :contradiction
                                                               :description "Marc's brother said he wasn't home."
                                                               :confidence-score 0.8}]))
(fact "json-to-map makes a list of jsons into list of maps"
      (json-to-map examples)=> (contains [{:chapter 1,
                                           :confidence-score 0.7,
                                           :description "Marc says he has been home at the time of murder.",
                                           :evidence-type "alibi",
                                           :suspect "Marc"}
                                          {:chapter 2,
                                           :confidence-score 0.6,
                                           :description "Anna had a fight with the victim.",
                                           :evidence-type "motive",
                                           :suspect "Anna"}
                                          {:chapter 2,
                                           :confidence-score 0.8,
                                           :description "Marc's brother said he wasn't home.",
                                           :evidence-type "contradiction",
                                           :suspect "Marc"}
                                          {:chapter 4,
                                           :confidence-score 0.55,
                                           :description "A love letter addressed to the victim was found with her signature on it. ",
                                           :evidence-type "contradiction",
                                           :suspect "Ellen"}
                                          {:chapter 5,
                                           :confidence-score 0.89,
                                           :description "Peter was working the night of the murder.",
                                           :evidence-type "alibi",
                                           :suspect "Peter"}])) 