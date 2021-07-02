class BooksInstance < ApplicationRecord
  belongs_to :book
  belongs_to :user
  belongs_to :user
end
