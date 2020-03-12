(ns com.example.model.category
  (:require
    [com.fulcrologic.rad.attributes :as attr :refer [defattr]]
    [com.fulcrologic.rad.authorization :as auth]
    [com.wsscode.pathom.connect :as pc]
    #?(:clj [com.example.components.database-queries :as queries])))

(defattr id :category/id :long
  {::attr/identity?                                          true
   :com.fulcrologic.rad.database-adapters.datomic/native-id? true
   :com.fulcrologic.rad.database-adapters.datomic/schema     :production})

(defattr label :category/label :string
  {::attr/required?                                          true
   :com.fulcrologic.rad.database-adapters.datomic/entity-ids #{:category/id}
   :com.fulcrologic.rad.database-adapters.datomic/schema     :production})

(defattr all-categories :category/all-categories :ref
  {::attr/target :category/id
   ::pc/output   [{:category/all-categories [:category/id]}]
   ::pc/resolve  (fn [{:keys [query-params] :as env} _]
                   #?(:clj
                      {:category/all-categories (queries/get-all-categories env query-params)}))})

(def attributes [id label all-categories])

