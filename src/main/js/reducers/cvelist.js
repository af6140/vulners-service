function cvelistReducer(state={cves: [] }, action) {
  console.log("cvelistReducer: *********");
  console.log(action)
  console.log(state);
  switch (action.type) {
    case 'CVELIST_READY':
      console.log("Reducer: cvelist ready")
      return {
        cves: action.payload.cves
      }
    default:
      if(state==null){
        state = {
          cves: []
        }
      }
      return state;
  }
}

export default cvelistReducer
