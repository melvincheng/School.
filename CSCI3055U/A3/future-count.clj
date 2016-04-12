(def character
	(map char (range 33 126)))

(defn rand-char []
	(rand-nth character))

(defn rand-string [length]
	(apply str (repeatedly length #(rand-char))))

(defn rand-strArr[size length]
	(repeatedly size #(rand-string length)))

(defn future-count [thread data]
	(def modulus (mod (count data) thread))
	(if (= modulus 0) (def K (quot (count data) thread)))
	(if (not= modulus 0)(def K (+ (quot (count data) thread) 1)))
	(apply + (apply concat(doall(map deref(doall(map #(future(map count(map distinct %)))(partition K K nil data ))))))))

(doseq [i(range 1 2)] (time(future-count i(rand-strArr 10000000 10))))
(doseq [i(range 2 66)] (time(future-count i(rand-strArr 10000000 10))))

(shutdown-agents)