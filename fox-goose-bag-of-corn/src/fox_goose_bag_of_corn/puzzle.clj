(ns fox-goose-bag-of-corn.puzzle)

(def start-pos [[[:fox :goose :corn :you] [:boat] []]])

(defn rm [coll cargo]
  (vec (remove (set [cargo :you]) coll)))

(defn add [coll cargo]
  (vec (filter identity (concat coll [cargo :you]))))

(defn sail-forward
  [cargo steps]
  (let [[left-shore river right-shore] (last steps)
        sail [(rm left-shore cargo)
              (add river cargo)
              right-shore]
        dock [(first sail)
              river
              (add right-shore cargo)]]
    (conj steps sail dock)))

(defn sail-back
  [cargo steps]
  (let [[left-shore river right-shore] (last steps)
        sail [left-shore
              (add river cargo)
              (rm right-shore cargo)]
        dock [(add left-shore cargo)
              river
              (last sail)]]
    (conj steps sail dock)))

(defn sail
  ([forward steps]
   (->> steps
        (sail-forward forward)
        (sail-back nil)))
  ([forward back steps]
   (->> steps
        (sail-forward forward)
        (sail-back back))))

(defn river-crossing-plan []
  (->> start-pos
       (sail :goose)
       (sail :corn :goose)
       (sail :fox)
       (sail-forward :goose)))

