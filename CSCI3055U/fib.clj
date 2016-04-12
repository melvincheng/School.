; (defn fib [n]
; 	(cond
; 	(= n 0) 1
; 	(= n 1) 1
; 	:else (+ (fib (- n 1)) (fib (- n 2)))))

; (println (fib 15))


(defn fib-tail 
	([n]
	(if (< n 2)	1
		(fib-tail (dec n) 1 1)))
	([counter F-now F-prev]
		(cond
			(zero? counter) F-now
			:else (fib-tail (dec counter) (bigint (+ F-now F-prev)) F-now))))

(defn fib-recur
	([n]
	(if (< n 2)	1
		(fib-recur (dec n) 1 1)))
	([counter F-now F-prev]
		(cond
			(zero? counter) F-now
			:else (recur (dec counter) (bigint (+ F-now F-prev)) F-now))))


(defn fib-loop [n]
	(if (< n 2) 1
		(loop [a (bigint 1) b (bigint 1) j 2]
		(if (= j n) a (recur (+ a b) a (inc j))))))


; (println (fib-tail 5))
; (println (fib-recur 5))
(println (fib-loop 5))