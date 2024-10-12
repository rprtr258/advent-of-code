def answer [] {
    let all_digits = $in | split chars | filter {|c| '0123456789' | str contains $c} | into int
    let first_digit = $all_digits | first
    let last_digit = $all_digits | last
    $first_digit * 10 + $last_digit
}
let lines = open day1.in | split row "\n" | drop 1
$lines | par-each {|line| $line | answer } | reduce {|it, acc| $it + $acc} | into string | print -n "1 part: " $in
print ''
$lines | each {|| 
    $in
        | str replace -a 'one' 'one1one'
        | str replace -a 'two' 'two2two'
        | str replace -a 'three' 'three3three'
        | str replace -a 'four' 'four4four'
        | str replace -a 'five' 'five5five'
        | str replace -a 'six' 'six6six'
        | str replace -a 'seven' 'seven7seven'
        | str replace -a 'eight' 'eight8eight'
        | str replace -a 'nine' 'nine9nine'
} | par-each {|| $in | answer } | reduce {|it, acc| $it + $acc} | into string | print -n "2 part: " $in
