import { combineReducers } from 'redux'
import pkgqueryformReducer from './pkgqueryform'
import cvelistReducer from './cvelist'

const appReduer = combineReducers({
  pkgqueryformReducer,
  cvelistReducer
})

export default appReduer
