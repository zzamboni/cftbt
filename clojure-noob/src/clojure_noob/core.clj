(ns clojure-noob.core
  (:gen-class))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "I'm a little teapot!"))

(println "Cleanlines is next to godliness")

(defn train
  []
  (println "Choo choo!"))

(+ 1 (* 2 3) 4)

;; Conditionals

(if false
  "By Zeus's hammer!"
  "By Aquaman's trident!")

(if true
  (do (println "Success!")
      "By Zeus's hammer!")
  (do (println "Failure!")
      "By Aquaman's trident!"))

(when true
  (println "Success!")
  "abra cadabra")

;; Functions

(defn error-message
  [severity]
  (str "OH GOD! IT'S A DISASTER! WE'RE "
       (if (= severity :mild)
         "MIDLY INCONVENIENCED!"
         "DOOOOOMED!")))

(def name "Chewbacca")

;; Multi-arity functions

(defn x-chop
  "Describe the kind of chop you're inflicting on someone"
  ([name chop-type]
   (str "I " chop-type " chop " name "! Take that!"))
  ([name]
   (x-chop name "karate")))

;; Rest parameters

(defn codger-communication
  [whippersnapper]
  (str "Get off my lawn, " whippersnapper "!!!"))

(defn codger
  [& whippersnappers]
  (map codger-communication whippersnappers))

(defn favorite-things
  [name & things]
  (str "Hi, " name ", here are my favorite things: "
       (clojure.string/join ", " things)))

;; Vector destructuring

;; Return the first element of a collection
(defn my-first
  [[first-thing]] ; Notice that first-thing is within a vector
  first-thing)

(defn chooser
  [[first-choice second-choice & unimportant-choices]]
  (println (str "Your first choice is: " first-choice))
  (println (str "Your second choice is: " second-choice))
  (println (str "We're ignoring the rest of your choices. "
                "Here they are in case you need to cry over them: "
                (clojure.string/join ", " unimportant-choices))))

;; Map destructuring

(defn announce-treasure-location
  [{lat :lat lng :lng}]
  (println (str "Treasure lat: " lat))
  (println (str "Treasure lng: " lng)))

(defn announce-treasure-location2
  [{:keys [lat lng]}]
  (println (str "Treasure lat: " lat))
  (println (str "Treasure lng: " lng)))

(defn receive-treasure-location
  [{:keys [lat lng] :as treasure-location}]
  (println (str "Treasure lat: " lat))
  (println (str "Treasure lng: " lng))
  (println (str "Location: " treasure-location)))

;; Anonymous functions

(map (fn [name] (str "Hi, " name))
     ["Darth Vader" "Mr. Magoo"])

(map #(str "Hi, " %)
     ["Darth Vader" "Mr. Magoo"])

((fn [x] (* x 3)) 8)

(#(* % 3) 8)

(#(identity %&) 1 "blarg" :yip)

;; Returning functions

(defn inc-maker
  "Create a custom incrementor"
  [inc-by]
  #(+ % inc-by))
