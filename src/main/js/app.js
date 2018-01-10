import React from 'react';
import ReactDOM from 'react-dom';
import { createStore, applyMiddleware, compose } from 'redux';
import { Provider } from 'react-redux'
import appReduer from './reducers/app'
import rootSaga from './middlware/sagas'
import createSagaMiddleware from 'redux-saga'

import MuiThemeProvider from 'material-ui/styles/MuiThemeProvider';
import CVEAppBar from './components/CVEAppBar.js'
import PackageQueryComponent from './containers/PackageQueryComponent.js'
import CVEListContainer from './containers/CVEListContainer.js'

const client = require('./client');

class Home extends React.Component {

	constructor(props) {
		super(props);
		this.state = { };
	}

	componentDidMount() {
//		client({method: 'GET', path: '/api/employees'}).done(response => {
//			this.setState({employees: response.entity._embedded.employees});
//		});
	}

	render() {
		return(
            <div>
                <CVEAppBar/>
                <PackageQueryComponent />
                <CVEListContainer />
            </div>
		)
	}
}

const App = ( ) => (
    <MuiThemeProvider>
        <Home />
    </MuiThemeProvider>
)

const initialState = {
	pkgqueryformReducer: {
		pkg_name : '',
		pkg_version : ''
	},
	cvelistReducer: {
		cves: []
	}
}
const sagaMiddleware = createSagaMiddleware()
const store = createStore(appReduer, initialState,   compose(applyMiddleware(sagaMiddleware), window.devToolsExtension ? window.devToolsExtension() : f => f));
sagaMiddleware.run(rootSaga)

ReactDOM.render(
	<Provider store={store}>
   <App />
	</Provider>,
   document.getElementById('react')
);
