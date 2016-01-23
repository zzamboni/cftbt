(ns cftbt-exercises.core
  (:gen-class))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))

;; Chapter 3

;;; Ex. 4
(defn mapset
  "Apply a function over all the elements in a seq, returning the result as a set"
  [fn seq]
  (set (map fn seq)))

(defn mapset2
  "Apply a function over all the elements in a seq, converting it to a set first, and returning the results as a set"
  [fn seq]
  (set (map fn (set seq))))
