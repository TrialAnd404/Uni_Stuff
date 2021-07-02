json.extract! books_instance, :id, :book_id, :shelfmark, :purchased_at, :user_id, :user_id, :checkout_at, :due_at, :returned_at, :created_at, :updated_at
json.url books_instance_url(books_instance, format: :json)
