(in-ns 'csci3055u.a2)
(clojure.core/refer 'clojure.core)

(defn quicksort [comparator & coll]
	(cond
		(<= (count coll) 1.0) coll
		:else (concat (apply quicksort comparator (filter #(= (comparator % (nth coll (quot (count coll) 2))) -1) coll))
			(filter #(= (comparator % (nth coll (quot (count coll) 2))) 0) coll)
			(apply quicksort comparator (filter #(= (comparator % (nth coll (quot (count coll) 2))) 1) coll)))
		)
	)