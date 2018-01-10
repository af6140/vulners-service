import React from 'react';
import { connect } from 'react-redux';
import {bindActionCreators} from 'redux';
import PropTypes from 'prop-types';
import CVEList from '../components/CVEList.js';

class CVEListContainer extends React.Component {

 constructor(props) {
 		super(props);
 		this.state = {
 		    cves: []
 		};
 }

 componentDidMount() {
 //		client({method: 'GET', path: '/api/employees'}).done(response => {
 //			this.setState({employees: response.entity._embedded.employees});
 //		});
 }

 	render() {    
    if(this.props.cves.length >0){
   		return <CVEList cves={this.props.cves} />;
    }else {
      return null;
    }
 	}
}
// CVEListContainer.propTypes = {
//   // Injected by React Redux
// }

function mapStateToProps (state, ownProps) {
  console.log("mapping state to props ");
  // this is the redux stat not this.state, combineReducers
  console.log(state.cvelistReducer);
  return {
    cves: state.cvelistReducer.cves
  }
}

function mapDispatchToProps(dispatch) {
  console.log("mapping dispatch to props");
  return {
   //resetPackageSearchFormAction: bindActionCreators(resetPackageSearchForm, dispatch),
  }
}

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(CVEListContainer);
