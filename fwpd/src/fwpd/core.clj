(ns fwpd.core)
(def filename "suspects.csv")

(def vamp-keys [:name :glitter-index])

(defn str->int
  [str]
  (Integer. str))

(def conversions {:name identity
                  :glitter-index str->int})

(def validations {:name (complement clojure.string/blank?)
                  :glitter-index (fn [s] (and (re-find #"^\d+$" s) true))})

(defn convert
  [vamp-key value]
  ((get conversions vamp-key) value))

(defn parse
  "Convert a CSV into rows of columns"
  [string]
  (map #(clojure.string/split % #",")
       (clojure.string/split string #"\n")))

(defn mapify
  "Return a seq of maps like {:name \"Edward Cullen\" :glitter-index 10}"
  [rows]
  (map (fn [unmapped-row]
         (reduce (fn [row-map [vamp-key value]]
                   (assoc row-map vamp-key (convert vamp-key value)))
                 {}
                 (map vector vamp-keys unmapped-row)))
       rows))

(defn glitter-filter
  [minimum-glitter records]
  (filter #(>= (:glitter-index %) minimum-glitter) records))

(defn suspect-names
  [records]
  (map :name records))

(defn append
  [records newrec]
  (if (validate validations newrec)
    (conj records newrec)
    records))

(defn validate
  [validations record]
  (every? true? (map #((% validations) (str (% record))) (keys validations))))

(defn rec->csv
  [vamp-keys record]
  (clojure.string/join "," (map #(% record) vamp-keys)))

(defn recs->csv
  [vamp-keys records]
  (clojure.string/join "\n" (map #(rec->csv vamp-keys %) records)))
