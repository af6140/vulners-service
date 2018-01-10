export const PKG_SEARCH_FORM_REQUEST = 'PKG_SEARCH_FORM_REQUEST'
export const PKG_SEARCH_FORM_RESET = 'PKG_SEARCH_FORM_RESET'

/* action creators
*/

export function resetPackageSearchForm() {
  console.log("action creator resetPackageSearchForm")
  return {
    type: PKG_SEARCH_FORM_RESET,
    payload: { }
  }
}

export function requestPackageSearch(pkg_name, pkg_version) {
  console.log("action creator requestPackageSearch");
  console.log("param: "+pkg_name);
  return {
    type: PKG_SEARCH_FORM_REQUEST,
    payload: {
      pkg_name: pkg_name,
      pkg_version: pkg_version
    }
  }
}
