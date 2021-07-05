Rails.application.routes.draw do
	resources :books_instances do
		member do
			get 'lending'
			get 'reserving'
		end
	end
  	resources :users
  	resources :authors
  	resources :books
  	# For details on the DSL available within this file, see https://guides.rubyonrails.org/routing.html
end
