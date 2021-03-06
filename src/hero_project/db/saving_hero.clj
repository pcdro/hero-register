(ns hero-project.db.saving-hero
  (:require [hero-project.protocols.storage-hero :as storage-hero]))

(defn add-hero! [account storage]
  (storage-hero/put! storage
                     #(assoc % (:id account) account)))

(defn heroes [storage]
  (storage-hero/read-all storage))

(defn hero [hero-id storage]
  (get (storage-hero/read-all storage) hero-id))
