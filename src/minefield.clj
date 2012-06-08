(ns minefield
  (:use clojure.contrib.combinatorics)
  (:use clojure.test))

(with-test
  (defn html-cell [cell] [:td {:class 
      (if (= cell \*) "mine"
        (str "mines-" cell))
    } cell])
  (is (= (html-cell 3) [:td {:class "mines-3"} 3]))
  (is (= (html-cell \*) [:td {:class "mine"} \*]))
)

(defn rows [minefield] (count minefield))
(defn cols [minefield] (count (first minefield)))

(defn outside? [cells cell]
  (or (< (second cell) 0)
      (>= (second cell) (cols cells))
      (< (first cell) 0)
      (>= (first cell) (rows cells))))

(defn mine? [cells cell]
  (if (outside? cells cell) false
      (= ((cells (first cell)) (second cell)) \*)))

(with-test 
  (defn neighbours [cell]
    (cartesian-product (range (dec (first cell)) (+ (first cell) 2))
                       (range (dec (second cell)) (+ (second cell) 2))))
  (is (= (neighbours [2 2]) [[1 1] [1 2] [1 3] [2 1] [2 2] [2 3] [3 1] [3 2] [3 3]]))
)

(with-test
  (defn count-neighbour-mines [cells cell]
    (count (filter #(mine? cells %) (neighbours cell))))
  (is (= (count-neighbour-mines [[\* 0 \*] [\* 0 0]] [0 1]) 3))
)

(defn minecell [cells cell] 
  (if (mine? cells cell) \*
      (count-neighbour-mines cells cell)))

(with-test
  (defn minefield [cells] 
    (map 
      (fn [row] (map (fn [col] (minecell cells [row col])) 
                     (range 0 (cols cells)))) 
      (range 0 (rows cells))))
  (is (= (rows (minefield [[0 0 0] [0 0 0]])) 2))
  (is (= (cols (minefield [[0 0 0] [0 0 0]])) 3))
  (is (= (minefield [[0 0 0] [0 0 0]]) [[0 0 0] [0 0 0]]))
  (is (= (minefield [[\* \* \*] [\* \* \*]]) [[\* \* \*] [\* \* \*]]))
  (is (= (minefield [[0 \* 0]]) [[1 \* 1]]))
  (is (= (minefield [[\* \* \*] [\* 0 \*] [\* \* \*]]) 
                    [[\* \* \*] [\* 8 \*] [\* \* \*]]))
)
