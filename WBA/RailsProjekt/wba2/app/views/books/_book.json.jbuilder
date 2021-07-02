json.extract! book, :id, :title, :publisher, :pub_year, :edition, :isbn, :created_at, :updated_at
json.url book_url(book, format: :json)
