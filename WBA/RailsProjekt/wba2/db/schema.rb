# This file is auto-generated from the current state of the database. Instead
# of editing this file, please use the migrations feature of Active Record to
# incrementally modify your database, and then regenerate this schema definition.
#
# This file is the source Rails uses to define your schema when running `bin/rails
# db:schema:load`. When creating a new database, `bin/rails db:schema:load` tends to
# be faster and is potentially less error prone than running all of your
# migrations from scratch. Old migrations may fail to apply correctly if those
# migrations use external dependencies or application code.
#
# It's strongly recommended that you check this file into your version control system.

ActiveRecord::Schema.define(version: 2021_07_02_115624) do

  create_table "authors", force: :cascade do |t|
    t.string "family_name"
    t.string "given_name"
    t.text "affiliation"
    t.datetime "created_at", precision: 6, null: false
    t.datetime "updated_at", precision: 6, null: false
  end

  create_table "authors_books", id: false, force: :cascade do |t|
    t.integer "author_id", null: false
    t.integer "book_id", null: false
    t.index ["author_id"], name: "index_authors_books_on_author_id"
    t.index ["book_id"], name: "index_authors_books_on_book_id"
  end

  create_table "books", force: :cascade do |t|
    t.string "title"
    t.string "publisher"
    t.integer "pub_year"
    t.integer "edition"
    t.string "isbn"
    t.datetime "created_at", precision: 6, null: false
    t.datetime "updated_at", precision: 6, null: false
  end

  create_table "books_instances", force: :cascade do |t|
    t.integer "book_id", null: false
    t.string "shelfmark"
    t.date "purchased_at"
    t.integer "lended_by_id"
    t.integer "reserved_by_id"
    t.datetime "checkout_at"
    t.date "due_at"
    t.datetime "returned_at"
    t.datetime "created_at", precision: 6, null: false
    t.datetime "updated_at", precision: 6, null: false
    t.index ["book_id"], name: "index_books_instances_on_book_id"
    t.index ["lended_by_id"], name: "index_books_instances_on_lended_by_id"
    t.index ["reserved_by_id"], name: "index_books_instances_on_reserved_by_id"
  end

  create_table "users", force: :cascade do |t|
    t.string "family_name"
    t.string "given_name"
    t.text "address"
    t.string "email"
    t.string "password"
    t.boolean "blocked"
    t.text "remarks"
    t.datetime "created_at", precision: 6, null: false
    t.datetime "updated_at", precision: 6, null: false
  end

  add_foreign_key "books_instances", "books"
  add_foreign_key "books_instances", "users", column: "lended_by_id"
  add_foreign_key "books_instances", "users", column: "reserved_by_id"
end
