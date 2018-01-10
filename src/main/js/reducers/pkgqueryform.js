//slice reducer, set default state etc
function pkgqueryformReducer(state={pkg_name: '', pkg_version: '', errorMessage: '' }, action) {
  console.log("pkgqueryformReducer: *********");
  console.log(action)
  console.log(state);
  switch (action.type) {
    case 'PKG_SEARCH_FORM_RESET':
      console.log("Reducer: reset form")
      return {
        pkg_name: '',
        pkg_version: '',
        errorMessage: ''
      }
    case 'PKG_SEARCH_DONE':
      console.log("Reducer: search with payload");
      console.log(action.payload);
      state = Object.assign( {}, state,
        { pkg_name: action.payload.pkg_name,
          pkg_version: action.payload.pkg_version
        }
      );
      return state;
    default:
      if(state==null){
        state = {
          pkg_name: '',
          pkg_version: '',
          errorMessage: ''
        }
      }
      return state;
  }
}

export default pkgqueryformReducer
