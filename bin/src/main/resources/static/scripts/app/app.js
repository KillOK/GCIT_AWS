var LibraryApp = angular.module("LibraryApp", ["ngRoute"]);

LibraryApp.config(["$routeProvider", function($routProvider){
    return $routProvider
    .when("/",{
        redirectTo:"/home"
    })
    .when("/home",{
    	templateUrl:"home.html"
    })
    .when("/librarian",{
    	templateUrl:"librarianbranch/librarian.html"
    })
    .when("/admin",{
    	templateUrl:"adminbranch/admin.html"
    })
    .when("/borrower",{
    	templateUrl:"borrowerbranch/borrower.html"
    })
    .when("/booksCRUD",{
    	templateUrl:"adminbranch/booksCRUD.html"
    })
    .when("/authorsCRUD",{
    	templateUrl:"adminbranch/authorsCRUD.html"
    })
    .when("/genresCRUD",{
    	templateUrl:"adminbranch/genresCRUD.html"
    })
    .when("/branchCRUD",{
    	templateUrl:"adminbranch/branchCRUD.html"
    })
    .when("/borrowerCRUD",{
    	templateUrl:"adminbranch/borrowerCRUD.html"
    })
    .when("/overrideDueDate",{
    	templateUrl:"adminbranch/overrideDueDate.html"
    })
}])
    

LibraryApp.controller("authorController", function($scope, $http){

    
    $http.get("http://localhost:8080/lms/readAuthors").success(function(result){
        $scope.authors = result;
    })
    
    $scope.deleteAuthor = function (author) {
        var authorToDelete = author;
        authorToDelete.authorName = null;
        authorToDelete.books = null;
        $http.post("http://localhost:8080/lms/saveAuthor", authorToDelete).success(function (data) {
            $http.get("http://localhost:8080/lms/readAuthors").success(function (result) {
                $scope.authors = result;
            })
        })
    }
})
















