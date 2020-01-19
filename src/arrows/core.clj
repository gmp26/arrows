(ns arrows.core)

(def grid
  "Enter grid contents as compass points"
  [[[:s :w :se :n]
    [:s :ne :sw :ne]
    [:e :e :se :s]
    [:w :s :n :ne]]

   [[:n :se :e :sw]
    [:e :se :n :s]
    [:sw :nw :s :n]
    [:ne :e :s :se]]])


(def arrow
  "Convert compass points to movements using this table (used in move-under).
  [0 0] is North West corner. [3 3] is South East corner"
  {:n  [0 -1]
   :ne [1 -1]
   :e  [1 0]
   :se [1 1]
   :s  [0 1]
   :sw [-1 1]
   :w  [-1 0]
   :nw [-1 -1]})

(defn arrow-under
  "The direction arrow under grid g at position [x y]"
  [g x y]
  (get-in grid [g y x]))                                    ; reverse x and y to stay mathsy

(defn move-under
  "The move under grid g at position [x y]"
  [g x y]
  (arrow (arrow-under g x y)))

(defn move-counter [counter movement]
  (mapv + counter movement))

; From each state there are two available moves 0 and 1, selected by b
(defn move [[[x0 y0 :as c0] [x1 y1 :as c1] :as state] b]
  (if (zero? b)
    [(move-counter c0 (apply move-under 1 c1)) c1]
    [c0 (move-counter c1 (apply move-under 0 c0))]))

(defn follow-path [position path]
  (if (empty? path)
    position
    (follow-path (move position (first path)) (rest path))))

(defn print-path [from path]
  (let [step (first path)]
    (when step
      (println from "->" (move from step) "using"
        (apply arrow-under (- 1 step) (from (- 1 step)))
        (if (zero? step) "right" "left"))
      (print-path (move from step) (rest path)))))

(defn search-for-solution [start finish paths visited]
  (let [path (first paths)
        remaining-paths (rest paths)
        on-grid? (fn [[x y]]
                   (let [max-grid-index (dec (count (get-in grid [0 0])))]
                     (and (<= 0 x max-grid-index) (<= 0 y max-grid-index))))
        invalid? (fn [[c0 c1]]
                     (not (and (on-grid? c0) (on-grid? c1))))]
    (if path
      (let [position (follow-path start path)]
        (cond
          ; are we there yet?
          (= position finish)
          (print-path start path)

          ; have we been here before? or ae we off-grid?
          (or (visited position) (invalid? position))
          (search-for-solution start finish remaining-paths visited) ;continue searching

          :else
          (search-for-solution                              ; keep searching
            start
            finish
            (conj remaining-paths                           ; having queued two new paths to try
              (conj path 0)
              (conj path 1))
            (assoc visited position path))                  ; and remembering where we've been
          ))
      (println "No solution found"))))

(defn find-route
  "Look for a path from the start position to the end position. See examples below.
  Kick things off by adding paths [0] and [1] to the search queue, having visited nowhere yet."
  [start finish]
  (search-for-solution start finish [[0] [1]] {}))

(comment
  ;
  ; Experiment by running these code snippets in the REPL
  ;

  "This is the puzzle as set"
  (find-route
    [[0 0] [0 0]]
    [[3 3] [3 3]]
    )
  ;=>  The solution is  [1 0 0 1 1 0 0 0 1 1 0 0 1 1 0 1 0 0 1 0 1 0 0 0 1 1 0 1 0 0 1 1 0 0 0 1 1 1 0 1 0 1 1 0 1 1 0 0 1 0 0 0]

  (find-route
    [[3 3] [3 3]]
    [[0 0] [0 0]])
  ;=> No solution found

  (find-route
    [[0 3] [0 3]]
    [[3 0] [3 0]])
  ;=> The solution is  [0 1 1 1 0 1 0 0 1 0 0 1 1 0 0 1 1 0 1 0 0 1 0 1 0 0 0 1 1 0 1 0 0 1 1]

  (find-route
    [[3 0] [3 0]]
    [[0 3] [0 3]])
  ;=> No solution found

  (arrow-under 0 0 0)
  ; => :s
  (arrow-under 0 3 0)
  ; => :n

  (move-under 0 0 0)
  ; => :s
  (move-under 0 3 0)
  ; => :n

  (valid? [-1 0])
  ; => false
  (valid? [2 3])
  ; => true
  (valid? [2 4])
  ; => false

  ;;
  ;; Generate new puzzles...
  ;;

  ; redefine grids to 2 x 2
  (def grid [[[:s :n] [:e :w]]
             [[:s :n] [:e :w]]])

  ; it's important to redefine grid bounds too
  (defn on-grid? [[x y]]
    (let [max-grid-index (dec (count (get-in grid [0 0])))]
      (and (<= 0 x max-grid-index) (<= 0 y max-grid-index))))

  ; Try these...
  (find-route [[0 0] [0 0]] [[1 1] [1 1]])
  (find-route [[1 1] [1 1]] [[0 0] [0 0]])
  (find-route [[0 0] [0 0]] [[0 1] [1 0]])
  (find-route [[0 0] [0 0]] [[1 0] [0 1]])
  (find-route [[0 0] [0 0]] [[0 0] [1 1]])
  (find-route [[0 0] [0 0]] [[1 1] [0 0]])
  )