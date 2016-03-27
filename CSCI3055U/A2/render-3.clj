(defprotocol render-protocol
	(render [x])
)

(extend-protocol render-protocol
	Long
	(render [x] (str "INTEGER["x"]"))
	Double
	(render [x] (str "FLOAT["x"]"))
	String
	(render [x] x)
	clojure.lang.PersistentVector
	(render [x] (clojure.string/join " "(map render x)))
	Object
	(render [x] str "BLANK")
	)

(println (render 10.0))
(println (render 10))
(println (render "10"))
(println (render [1,2.0,"3"]))
(println (render 'a))