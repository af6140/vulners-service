import { delay } from 'redux-saga'
import { call, put, takeEvery, takeLatest } from 'redux-saga/effects'
const client = require('../client');


export function* helloSaga() {
  console.log('Hello Saga!')
}

export function* fetchCVESForPackage(action) {
  console.log("saga search action: ", action);
  const pkg_name = action.payload.pkg_name;
  const pkg_version = action.payload.pkg_version;
  try {
    const response = yield call(client, {path:'/cve-list?name={name}&version={version}', method: 'GET', params: {name: pkg_name, version: pkg_version}});
    console.log('response: ', response.entity, 'status=', response.status.code)
    if(response.status.code==200){
      yield put({type: "CVELIST_READY", payload: { cves: response.entity}})
    }else if (response.status.code == 204) {
      yield put({type: "CVELIST_READY", payload: { cves: [] }})
    }else {
      yield put({type: "CVELIST_READY", payload: { cves: [] }})
    }
  }catch(e) {
    console.log(e);
    return;
  }
}

function* watchFetchCVESForPackage() {
  yield takeLatest('PKG_SEARCH_FORM_REQUEST', fetchCVESForPackage);
}

export default function* rootSaga() {
  yield [
    helloSaga(),
    watchFetchCVESForPackage(),
  ]
}
