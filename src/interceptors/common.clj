(ns interceptors.common
  (:require [io.pedestal.http.body-params :as http.body-params]))

(def body-parser (http.body-params/body-params))
