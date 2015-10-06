(ns alphabet-cipher.coder_kephren)

(def alphabet (char-array "abcdefghijklmnopqrstuvwxyz"))

(def offset (int \a))

(defn nth-alpha [index]
  (nth alphabet (mod index 26)))

(defn a->idx [alpha]
  (- (int alpha) offset))

(defn seq->str [seq] (apply str seq))

(defn sieve [fn left right]
  (seq->str (map #(nth-alpha (fn (a->idx %1)
                                 (a->idx %2)))
                 left right)))

;the-michael-de-cycle-plus
(defn de-cycle [raw]
  (loop [size 1]
    (let [keyword (seq->str (take size raw))
          tail (seq->str (take size (drop size raw)))]
      (if (or (= keyword tail)
              (= raw (str keyword tail) ))
        keyword
        (recur (inc size))))))

(defn de-cycle-prototype [raw]
  (loop [front ""
         keyword raw
         back ""]
    (let [next-front (seq->str (take (inc (count front)) keyword))
          next-back (seq->str (take-last (inc (count back)) keyword))]
      (println "->" next-front keyword next-back)
      (cond
        (= next-front keyword) keyword
        (and (in? next-front next-back)
             (not= front next-back)
             ) (recur front (seq->str (drop-last (count next-back) keyword)) "")
        :else (recur next-front keyword next-back)))))

(defn dumb-de-cycle [raw]
  (let [full-size (count raw)]
    (loop [size 1]
      (if (= (seq->str (->> raw (take size) (cycle) (take full-size)))
             raw)
        (seq->str (take size raw))
        (recur (inc size))))))

(defn encode [keyword message]
  "encodeme"
  (sieve + (cycle keyword) message))

(defn decode [keyword message]
  "decodeme"
  (sieve - message (cycle keyword)))

(defn decypher [cypher message]
  "decypherme"
  (de-cycle (sieve - cypher message)))
