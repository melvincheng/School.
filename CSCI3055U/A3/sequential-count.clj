; (defn distinctCount [data] (map str data))

(def data ["asdas" "jgfk" "onds"])
; (println distinctCount data)

(defn sequential-count [data] (reduce +(map count(map distinct data))))

(println (sequential-count data))