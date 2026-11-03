(ns your-own-detective.core-test
  (:require [clojure.test :refer :all]
            [your-own-detective.core :refer :all]
            [midje.sweet :refer :all]))


(fact  "proba" (+ 1 1) => 2)

(fact "get-evidence-by-chapter returns all evidence in a certain chapter" 
      (get-evidence-by-chapter 2 examples) => (get examples 2))


(fact "get-evidence-up-to-chapter returns all evidence in a certain chapter and in the chapters preceding it"
      (get-evidence-up-to-chapter 5 examples) => (contains [
                                                          {:suspect "Marc"
                                                            :evidence-type :alibi
                                                            :description "Marc says he has been home at the time of murder."}
                                                          {:suspect "Anna"
                                                            :evidence-type :motive
                                                            :description "Anna had a fight with the victim."}
                                                          
                                                           {:suspect "Marc"
                                                            :evidence-type :contradiction
                                                            :description "Marc's brother said he wasn't home."}
                                                          {:suspect "Ellen"
                                                            :evidence-type :contradiction
                                                            :description "A love letter addressed to the victim was found with her signature on it. "}
                                                          {:suspect "Peter"
                                                            :evidence-type :alibi
                                                            :description "Peter was working the night of the murder."}]
      ))

(fact "get-evidence-by-type returns all evidence with a given type"
      (get-evidence-by-type :alibi examples) => (contains [
                                                            {:suspect "Marc"
                                                             :evidence-type :alibi
                                                             :description "Marc says he has been home at the time of murder."}
                                                             {:suspect "Peter"
                                                              :evidence-type :alibi
                                                              :description "Peter was working the night of the murder."}
                                                          
                                                            
      ]))