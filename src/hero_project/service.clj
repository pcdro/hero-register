(ns hero-project.service
  (:require [ring.util.response :as ring-resp]
            [io.pedestal.http :as http]
            [io.pedestal.http.body-params :as body-params]
            [hero-project.controller :as controller]
            [hero-project.hero-adapter :as hero-adapter]
            [hero-project.interceptors.error-handler :as error-handler]
            [clojure.data.json :as json]))

(defn home-page
  [_]
  (ring-resp/response {:game-title "hero project"}))

(defn heroes
  [{{:keys [storage]} :components}]
  (let [heroes (controller/heroes storage)]
    (ring-resp/response (hero-adapter/heroes->hero-view heroes))))

(defn create-hero
  [{{:keys [name]} :json-params
    {:keys [storage]} :components}]
  (let [hero-created (controller/create-hero! name storage)]
    (ring-resp/response (hero-adapter/hero->hero-view hero-created))))

(def common-interceptors
  [(body-params/body-params)
   http/html-body
   error-handler/service-error-handler])

(def routes
  #{["/" :get (conj common-interceptors `home-page)]
    ["/heroes/" :get (conj common-interceptors `heroes)]
    ["/hero/" :post (conj common-interceptors `create-hero)]})