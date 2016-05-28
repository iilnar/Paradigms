(defn proto-get [obj key]
  (cond
    (contains? obj key) (obj key)
    (contains? obj :prototype) (proto-get (:prototype obj) key)))

(defn proto-call [obj key & args]
  (apply (proto-get obj key) (cons obj args)))

(defn method [key]
  (fn [obj & args] (apply (partial proto-call obj key) args)))

(defn field [key]
  (fn [obj] (proto-get obj key)))

(def evaluate (method :evaluate))
(def toString (method :toString))
(def diff (method :diff))

(defn constructor [ctor prototype]
  (fn [& args] (apply (partial ctor {:prototype prototype}) args)))

(declare Constant)
(def ConstantPrototype{
  :evaluate (fn [this arg] ((field :val) this))
  :diff (fn [this name] (Constant 0))
  :toString (fn [this] (str ((field :val) this)))
  })
(def Constant (constructor (fn[this num] (assoc this :val num)) ConstantPrototype))

(declare Variable)
(def VariablePrototype{
  :evaluate (fn [this arg] (get arg ((field :val) this)))
  :diff (fn [this name] (cond
                          (= name ((field :val) this)) (Constant 1)
                          :else (Constant 0)
                          ))
  :toString (fn [this] (str ((field :val) this)))
  })
(def Variable (constructor (fn [this val] (assoc this :val val)) VariablePrototype))

(declare Add)
(def AddPrototype{
  :evaluate (fn [this arg] (apply + (map (fn [x] (evaluate x arg)) ((field :val) this))))
  :diff (fn [this name] (apply Add (map (fn [x] (diff x name)) ((field :val) this))))
  :toString (fn [this] ( str "(+ " (apply print-str (map toString ((field :val) this))) ")"))
  })
(def Add (constructor (fn [this & val] (assoc this :val val)) AddPrototype))

(declare Subtract)
(def SubtractPrototype{
  :evaluate (fn [this arg] (apply - (map (fn [x] (evaluate x arg)) ((field :val) this))))
  :diff (fn [this name] (apply Subtract (map (fn [x] (diff x name)) ((field :val) this))))
  :toString (fn [this] ( str "(- " (apply print-str (map toString ((field :val) this))) ")"))
  })
(def Subtract (constructor (fn [this & val] (assoc this :val val)) SubtractPrototype))

(declare Multiply)
(def MultiplyPrototype{
  :evaluate (fn [this arg] (apply * (map (fn [x] (evaluate x arg)) ((field :val) this))))
  :diff (fn [this name] (cond
                          (= (count ((field :val) this)) 1 ) (diff (first ((field :val) this)) name)
                          :else (Add (apply Multiply (diff (first ((field :val) this)) name) (rest ((field :val) this)))
                                  (Multiply (diff (apply Multiply (rest ((field :val) this))) name) (first ((field :val) this)))))
          )
  :toString (fn [this] ( str "(* " (apply print-str (map toString ((field :val) this))) ")"))
  })
(def Multiply (constructor (fn [this & val] (assoc this :val val)) MultiplyPrototype))

(declare Divide)
(def DividePrototype{
  :evaluate (fn [this arg] (/ (evaluate ((field :a) this) arg) (evaluate ((field :b) this) arg)))
  :diff (fn [this name] (Divide
                          (Subtract (Multiply (diff ((field :a) this) name) ((field :b) this))
                            (Multiply (diff ((field :b) this) name) ((field :a) this)))
                          (Multiply ((field :b) this) ((field :b) this))))
  :toString (fn [this] ( str "(/ " (toString ((field :a) this)) " " (toString ((field :b) this)) ")"))
  })
(def Divide (constructor (fn [this a b] (assoc this :a a :b b)) DividePrototype))

(declare Negate)
(def NegatePrototype{
  :evaluate (fn [this arg] (- (evaluate ((field :a) this) arg)))
  :diff (fn [this name] (Negate (diff ((field :a) this) name)))
  :toString (fn [this] ( str "(/ " (toString ((field :a) this)) ")"))
  })
(def Negate (constructor (fn [this a] (assoc this :a a)) NegatePrototype))

(declare Sin)
(declare Cos)

(def SinPrototype{
  :evaluate (fn [this arg] (Math/sin (evaluate ((field :a) this) arg)))
  :diff (fn [this name] (Multiply (Cos ((field :a) this)) (diff ((field :a) this) name)))
  :toString (fn [this] ( str "(sin " (toString ((field :a) this)) ")"))
  })
(def Sin (constructor (fn [this a] (assoc this :a a)) SinPrototype))

(def CosPrototype{
  :evaluate (fn [this arg] (Math/cos (evaluate ((field :a) this) arg)))
  :diff (fn [this name] (Negate (Multiply (Sin ((field :a) this)) (diff ((field :a) this) name))))
  :toString (fn [this] ( str "(cos " (toString ((field :a) this)) ")"))
  })
(def Cos (constructor (fn [this a] (assoc this :a a)) CosPrototype))


(def operation{"+" Add "-" Subtract "negate" Negate "*" Multiply "/" Divide "sin" Sin "cos" Cos})
(defn niftyParser[s]
  (cond
    (seq? s) (apply (get operation (str (first s))) (map niftyParser (rest s)))
    (number? s) (Constant s)
    :else (Variable (str s)))
  )

(defn parseObject[s] (niftyParser (read-string s)))
