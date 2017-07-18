(ns hive.core
  (:require [quil.core :as q]
            [quil.middleware :as m]))

(def unitSize 15)
(def eqTriH 0.8660254)
(def board-width 600)
(def board-height 600)
(def cellsX  (int (/ board-width (* unitSize 3))))
(def cellsY  (int (/ board-height (+ (* unitSize eqTriH 2) eqTriH))))
(def TWOPI (* 2 Math/PI))

(defn setup []
  (q/smooth)
  ; Set frame rate to 30 frames per second.
  (q/frame-rate 30)
  ; Set color mode to HSB (HSV) instead of default RGB.
  (q/color-mode :hsb)
  ; setup function returns initial state. It contains
  ; circle color and position.
  {:color 0
   :angle 0})

(defn update-state [state]
  ; Update sketch state by changing circle color and position.
  {:color (mod (+ (:color state) 0.7) 255)
   :angle (+ (:angle state) 0.1)})

(defn draw-state [state]
  ; Clear the sketch by filling it with light-grey color.
  (q/background 240)
  ; Set circle color.
  (q/fill (:color state) 255 255)

  (dotimes [i cellsX]
    (dotimes [j cellsY]
      (q/begin-shape)
      (dotimes [k 6]
        (let [x (+ unitSize
                   (* unitSize i 3)
                   (* (q/cos (* (/ TWOPI 6) k)) unitSize))
              y (+ (* unitSize eqTriH)
                   (* j unitSize 2 eqTriH)
                   (* (q/sin (* (/ TWOPI 6 ) k)) unitSize))]
          (q/vertex x y )))
      (q/end-shape :close)
      (q/begin-shape)
      (dotimes [k 6] 
        (let [x (+ (* unitSize 2.5) (* i unitSize 3)
                   (* (q/cos (* (/ TWOPI 6) k)) unitSize))
              y (+ (* 2 unitSize eqTriH)
                   (* j unitSize 2 eqTriH)
                   (* (q/sin (* (/ TWOPI 6) k)) unitSize))]
          (q/vertex x y)))
      (q/end-shape :close))))

(q/defsketch hive
  :title "You spin my circle right round"
  :size [board-width board-height]
  ; setup function called only once, during sketch initialization.
  :setup setup
  ; update-state is called on each iteration before draw-state.
  :update update-state
  :draw draw-state
  :features [:keep-on-top]
  ; This sketch uses functional-mode middleware.
  ; Check quil wiki for more info about middlewares and particularly
  ; fun-mode.
  :middleware [m/fun-mode])

