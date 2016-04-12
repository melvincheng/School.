(def character
	(map char (range 33 126)))

(defn rand-char []
	(rand-nth character))

(defn rand-string [length]
	(apply str (repeatedly length #(rand-char))))

(defn rand-strArr[size length]
	(repeatedly size #(rand-string length)))

(defn sequential-count [data]
	(reduce +(map count(map distinct data))))

(doseq [i(range 0 10100000 100000)] (time(sequential-count (rand-strArr i 10))))