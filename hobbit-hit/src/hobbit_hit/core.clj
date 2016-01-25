(ns hobbit-hit.core
  (:gen-class))

(def asym-hobbit-body-parts [{:name "head" :size 3}
                             {:name "left-eye" :size 1}
                             {:name "left-ear" :size 1}
                             {:name "mouth" :size 1}
                             {:name "nose" :size 1}
                             {:name "neck" :size 2}
                             {:name "left-shoulder" :size 3}
                             {:name "left-upper-arm" :size 3}
                             {:name "chest" :size 10}
                             {:name "back" :size 10}
                             {:name "left-forearm" :size 3}
                             {:name "abdomen" :size 6}
                             {:name "left-kidney" :size 1}
                             {:name "left-hand" :size 2}
                             {:name "left-knee" :size 2}
                             {:name "left-thigh" :size 4}
                             {:name "left-lower-leg" :size 3}
                             {:name "left-achilles" :size 1}
                             {:name "left-foot" :size 2}])

(defn matching-part
  [part]
  {:name (clojure.string/replace (:name part) #"^left-" "right-")
   :size (:size part)})

;; Exercise 3.5
(def alien-parts ["left" "right" "top" "bottom" "middle"])

(defn matching-alien-parts
  [part]
  (map (fn [m] 
         {:name (clojure.string/replace (:name part) #"^left-" (str m "-"))
          :size (:size part)}) alien-parts))

(defn alienize-body-parts
  "Expects a seq of map that have a :name and :size"
  [asym-body-parts]
  (reduce (fn [final-body-parts part]
            (into final-body-parts (set (flatten [part (matching-alien-parts part)]))))
          []
          asym-body-parts))

;; Exercise 3.6
(defn multiplied-body-parts
  [num part]
  (map (fn [m] 
         {:name (clojure.string/replace (:name part) #"$" (str "-" m))
          :size (:size part)}) (range num)))

(defn multiply-body-parts
  "Expects a seq of map that have a :name and :size, and a number of how many of each to create"
  [num asym-body-parts]
  (reduce (fn [final-body-parts part]
            (into final-body-parts (multiplied-body-parts num part)))
          []
          asym-body-parts))

;; With loop and recurse
(defn symmetrize-body-parts
  "Expects a seq of maps that have a :name and :size"
  [asym-body-parts]
  (loop [remaining-asym-parts asym-body-parts
         final-body-parts []]
    (if (empty? remaining-asym-parts)
      final-body-parts
      (let [[part & remaining] remaining-asym-parts]
        (recur remaining
               (into final-body-parts
                     (set [part (matching-part part)])))))))

;; And now with reduce
(defn better-symmetrize-body-parts
  "Expects a seq of map that have a :name and :size"
  [asym-body-parts]
  (reduce (fn [final-body-parts part]
            (into final-body-parts (set [part (matching-part part)])))
          []
          asym-body-parts))

(defn hit
  [asym-body-parts genparts-fn]
  (let [sym-parts (genparts-fn asym-body-parts)
        body-part-size-sum (reduce + (map :size sym-parts))
        target (rand body-part-size-sum)]
    (loop [[part & remaining] sym-parts
           accumulated-size (:size part)]
      (if (> accumulated-size target)
        part
        (recur remaining (+ accumulated-size (:size (first remaining))))))))

(defn parse-int [s]
  (let [num (re-find #"\A-?\d+" s)]
    (when-not (nil? num) (Integer/parseInt num))))

(defn -main
  "Hit a hobbit. First arg tells how many times (defaults to 1). If second arg is given as 'alien', use radial parts from Ex. 3.5"
  ([]
   (println (str (hit asym-hobbit-body-parts better-symmetrize-body-parts))))
  ([ n & args]
   (let [fn (case (first args)
              "alien"    alienize-body-parts                               ; Alien 5-radial body, Ex. 3.5
              "multiply" (partial multiply-body-parts                      ; Generalized multiplied body parts, Ex. 3.6
                                  (or (parse-int (str (second args))) 1))
              better-symmetrize-body-parts)]                               ; Regular Hobbit body
       (doseq [x (range (or (parse-int (str n)) 1))]
         (println (str (hit asym-hobbit-body-parts fn)))))))


