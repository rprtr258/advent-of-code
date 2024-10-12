main :: () -> () {
    pipe (stdin) {
        iterate(read_word),
        map(parse::int),
        windowed(3),
        map(sum),
        windowed(2),
        map([x, y] -> y > x),
        sum,
        print,
    }
}

(->> *in*
    java.io.BufferedReader.
    line-seq
    (map (fn [s] (Integer/parseInt s)))
    (partition 3 1)
    (map (fn [xs] (reduce + xs)))
    (partition 2 1)
    (map vec)
    (map (fn [[x, y]] (< x y)))
    (map {false 0 true 1})
    (reduce +)
    print)

