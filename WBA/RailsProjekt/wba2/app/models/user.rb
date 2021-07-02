class User < ApplicationRecord
	has_many :book_instances
end
