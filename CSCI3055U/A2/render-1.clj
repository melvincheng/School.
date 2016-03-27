(defn render [x]
	(cond
		(= (type x) Long) (str "INTEGER["x"]")
		(= (type x) String) x
		(= (type x) Double) (str "FLOAT["x"]")
		(= (type x) clojure.lang.PersistentVector) (clojure.string/join " " (map render x))
		:else "BLANK")
	)

(println (render 10.0))
(println (render 10))
(println (render "10"))
(println (render [1,2.0,"3"]))
(println (render 'a))