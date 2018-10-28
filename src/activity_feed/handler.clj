(ns activity-feed.handler
  (:require [activity-feed.views :as views]
            [compojure.core :refer :all]
            [compojure.route :as route]
						[ring.middleware.defaults :refer [wrap-defaults site-defaults]]
						[ring.adapter.jetty :as jetty]
						[hiccup.middleware :refer [wrap-base-url]])
	(:gen-class))

(def base-url "/activity-feed")

(defroutes app-routes
	(GET "/"
		[]
		(views/home-page))
	(route/resources "/")
	(route/not-found "Not Found"))

(def app
	(wrap-base-url
		(wrap-defaults app-routes site-defaults) base-url))

(defn -main
	[& [port]]
	(let [port (Integer. (or port (System/getenv "PORT") 5000))]
		(println (str "Port: " port))
		(jetty/run-jetty #'app {:host "127.0.0.1"
														:port port
														:join? false})))
