(ns minefield
  (:use clojure.test)
  (:use clojure.contrib.combinatorics))

(defn html-cell2 [cell coord]
    [:td {:class "mines-1"} cell])


(defn html-cell [field coord]
  (html-cell2 ((field (first coord)) (second coord)) coord))

(defn rows [minefield] (range 0 (count minefield)))
(defn cols [minefield] (range 0 (count (first minefield))))

(defn minefield [] [[0 0 \*] [0 \? 0]])

(with-test
  (defn neighbours [coord]
    (cartesian-product (range (dec (first coord)) (+ 2 (first coord)))
                       (range (dec (second coord)) (+ 2 (second coord)))))
  (is (= (neighbours [2 3])
         [[1 2] [1 3] [1 4] [2 2] [2 3] [2 4] [3 2] [3 3] [3 4]]))
)

(with-test 
  (defn neighbour-mines [field coord]
    (count (filter #(mine? field %) (neighbours coord))))
  (is (= (neighbour-mines [[0 \*] [\* 0]] [1 1]) 2))
)

(with-test
  (defn mine? [field coord]
    (cond
      (< (first coord) 0) false
      (< (second coord) 0) false
      (>= (first coord) (count field)) false
      (>= (second coord) (count (first field))) false
      :else (= ((field (first coord)) (second coord)) \*)))
  (is (not (mine? [[0 \*] [0 0]] [0 0])))
  (is (mine? [[0 0 0] [0 0 \*]] [1 2]))
  (is (not (mine? [[0 0] [0 0]] [-1 0])))
  (is (not (mine? [[0 0] [0 0]] [0 -1])))
  (is (not (mine? [[0 0] [0 0]] [0 100])))
  (is (not (mine? [[0 0] [0 0]] [100 0])))
)

(defn show-cell [field coord]
  (if (mine? field coord) \* 
      (neighbour-mines field coord)))

(with-test
  (defn show-minefield [field]
    (map (fn [row] (map (fn [col] (show-cell field [row col]))
                        (cols field))) 
        (rows field)))
  (is (= (show-minefield [[\. \.] [\. \.] [\. \.]])
          [[0 0] [0 0] [0 0]]))
  (is (= (show-minefield [[\* \*] [\* \*]])
          [[\* \*] [\* \*]]))
  (is (= (show-minefield [[\. \.] [\. \*]])
          [[1 1] [1 \*]]))
)
