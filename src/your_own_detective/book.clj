(ns your-own-detective.book
  (:require [clojure.java.io :as io]))

(def folder (io/file "books/blue-train"))

(def files (.listFiles folder))

(defn names-only [files] (map #(.getName %) files))
(defn chapters-only-func
  [names]
  (keep #(re-matches #"chapter-(\d+)\.xhtml" %) names))
;(def chapters-only (chapters-only-func names-only))


(defn sort-chapters
  [chapters]
  (sort-by #(parse-long (second %)) chapters))
;(sort-chapters chapters-only)