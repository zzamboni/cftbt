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

;; Chapter 5

(def character
  {:name "Smooches McCutes"
   :attributes {:intelligence 10
                :strength 4
                :dexterity 5}})
(def c-int (comp :intelligence :attributes))
(def c-str (comp :strength :attributes))
(def c-dex (comp :dexterity :attributes))

;; Ex.1
(def attr #(comp % :attributes))
(def c-int2 (attr :intelligence))
(def c-str2 (attr :strength))
(def c-dex2 (attr :dexterity))

;; Ex.2
(defn my-comp
  ([] identity)
  ([f]
   (fn [& args]
     (apply f args)))
  ([f & others]
   (fn [& args]
     (apply f [(apply (apply my-comp others) args)]))))

;; Ex.3
(defn my-assoc-in
  [m [k & ks] v]
  (if (empty? ks)
    (assoc m k v)
    (assoc m k (my-assoc-in (or (get m k) {}) ks v))))

;; Ex.4
(def users [{:name "James" :age 26}  {:name "John" :age 43}])
                                        ; => #'cftbt-exercises.core/users
(update-in users [1 :age] inc)
                                        ; => [{:name "James", :age 26} {:name "John", :age 44}]

;; Ex.5
(defn my-update-in
  [m [k & ks] f & args]
  (if (empty? ks)
    (assoc m k (apply f (cons (get m k) (or args nil))))
    (assoc m k (apply (partial my-update-in (or (get m k) {}) ks f) args))))

;; Ch.7

;; Ex.2
(defn- debug
  [str]
  #_(println str))

(defn prec
  [op]
  (get {'+ 1
        '- 1
        '* 2
        '/ 2
        nil 0} op))

(declare infix)

(defn tree
  [outstack opstack]
  (let [_ (debug (str "tree: outstack=" outstack "   opstack=" opstack))
        operator (first opstack)
        first-operand (second outstack)
        second-operand (first outstack)
        new-elem (if (and (seq? first-operand) (= (first first-operand) operator))
                   (concat first-operand (list second-operand))
                   (list operator first-operand second-operand))
        result (conj (drop 2 outstack) new-elem)
        _ (debug (str "tree: result=" result))]
    result))

(defn output-trees
  [outstack opstack]
  (let [_ (debug (str "output-trees: outstack=" outstack "   opstack=" opstack))]
    (if (empty? opstack)
      outstack
      (output-trees (tree outstack opstack) (rest opstack)))))

(defn handle-operator
  [tok rest-exp outstack opstack]
  (let [p_opstack (prec (first opstack))
        p_tok (prec tok)
        _ (debug (str "handle-operator: tok=" tok "   p_opstack=" p_opstack "   p_tok=" p_tok))]
    (if (> p_opstack p_tok)
      (infix (conj rest-exp tok) (tree outstack opstack) (rest opstack))
      (infix rest-exp outstack (conj opstack tok))
      )))

(defn infix
  ([expr] (infix expr '() '()))
  ([[tok & rest-exp] outstack opstack]
   (let [_ (debug (str "infix: tok=" tok "  rest=" rest-exp "  outstack=" outstack "  opstack=" opstack))]
     (if (nil? tok)
       (first (output-trees outstack opstack))
       (if (number? tok)
         (infix rest-exp (conj outstack tok) opstack)
         (handle-operator tok rest-exp outstack opstack)
         )))))

(defmacro defattr
  "Define one character attribute function"
  [fname field]
  `(def ~fname (comp ~field :attributes)))

(defmacro defattrs
  "Define multiple character attribute functions"
  [& args]
  `(do
     ~@(map (fn [[p1 p2]] (list 'defattr p1 p2)) (partition 2 args))))
