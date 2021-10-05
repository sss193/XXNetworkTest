@startuml

namespace xxnetworktask {
        namespace di {
            interface MovieTaskComponent

            class MovieTaskModule

            class NetworkModule

            class LocalDbModule

            MovieTaskComponent *-- MovieTaskModule
            MovieTaskComponent *-- NetworkModule
            MovieTaskComponent *-- LocalDbModule

        }
        namespace presentation {

            namespace view {


                class HomeActivity { }

                class MovieDetailsActivity { }

                class MovieListActivity { }

            }

            namespace viewmodel {

                    class MovieListViewModel {
                        - movieTaskRepository: IMovieTaskRepository
                    }

                    class MovieDetailsViewModel {
                                            - movieTaskRepository: IMovieTaskRepository
                                        }
           }
        }


        namespace model {
                namespace datamodel {

                    class MovieListResponse {
                    - _page: Int
                     - _movieList: List<MovieDetailsResponse>
                          - _totalPages: String
                          - _totalResults: String

                    }
                    class MovieDetailsResponse {
                          - _id: Int
                          - _title: String
                            - _poster: String
                                - _description: String?
                                  - _rating: Double?
                    }
                 }
                namespace repo {

                    class MovieTaskRepository {
                         - remoteDataSource: IRemoteDataSource
                         - localDataSource: ILocalDataSource
                    }

                      interface IMovieTaskRepository {
          + getMovieDetails(id: Int): Single<MovieDetailsResponse>

          + getMovieListBySearchQuery(queryText: String, page: Int): Single<MovieListResponse>

          + getMovieListByGenre(genreId: Int, page: Int): Single<MovieListResponse>

          + getMovieWishList(): Single<MovieListResponse>

          + getMovieById(movieId: Int): Maybe<MovieEntity>

          + deleteMovieById(movieId: Int)

          + insertMovie(movie: MovieEntity)

          + deleteAllMovie()
                                        }
                }

                namespace remoteDatasource {
                    interface IRemoteDataSource {
                             + getMovieDetails(id: Int): Single<MovieDetailsResponse>

                             + getMovieListBySearchQuery(queryText: String, page: Int): Single<MovieListResponse>

                             + getMovieListByGenre(genreId: Int, page: Int): Single<MovieListResponse>
                    }
                    class RemoteDataSource {
                        - movieTaskApi: MovieTaskApi
                        - apiKey: String
                        - languageCode: String
                    }
                    interface MovieTaskApi {
                          + getMovieDetails( id: Int, api_key: String, language: String): Single<MovieDetailsResponse>

                              + getMovieListBySearchQuery(api_key: String, query: String, page: Int): Single<MovieListResponse>

                    }
                }


                namespace localDatasource {
                                    interface ILocalDataSource {
                                 + getMovieWishList(): Single<List<MovieEntity>>
                                   + getMovieById(movieId: Int): Maybe<MovieEntity>
                                   + deleteMovieById(movieId: Int)
                                   + insertMovie(movie: MovieEntity)
                                   + deleteAllMovie()
                                    }
                                    class LocalDataSource {
                                        - movieDAO: MovieDAO
                                        - exec: Executor
                                    }
                                }

        }
  }



xxnetworktask.presentation.view.MovieDetailsActivity o-- xxnetworktask.presentation.viewmodel.MovieDetailsViewModel

xxnetworktask.presentation.view.MovieListActivity o-- xxnetworktask.presentation.viewmodel.MovieListViewModel




xxnetworktask.presentation.viewmodel.MovieDetailsViewModel o-- xxnetworktask.model.repo.IMovieTaskRepository
xxnetworktask.presentation.viewmodel.MovieListViewModel o-- xxnetworktask.model.repo.IMovieTaskRepository

xxnetworktask.model.repo.IMovieTaskRepository <|.. xxnetworktask.model.repo.MovieTaskRepository

xxnetworktask.model.repo.MovieTaskRepository o-- xxnetworktask.model.remoteDatasource.IRemoteDataSource

xxnetworktask.model.repo.MovieTaskRepository o-- xxnetworktask.model.localDatasource.ILocalDataSource

xxnetworktask.model.localDatasource.ILocalDataSource <|.. xxnetworktask.model.localDatasource.LocalDataSource

xxnetworktask.model.remoteDatasource.IRemoteDataSource <|.. xxnetworktask.model.remoteDatasource.RemoteDataSource

xxnetworktask.model.remoteDatasource.RemoteDataSource o-- xxnetworktask.model.remoteDatasource.MovieTaskApi

@enduml