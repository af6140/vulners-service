import React from 'react';
import { connect } from 'react-redux';
import {bindActionCreators} from 'redux';
import PropTypes from 'prop-types'
import TextField from 'material-ui/TextField';
import RaisedButton from 'material-ui/RaisedButton';

import {resetPackageSearchForm, requestPackageSearch} from '../actions/app.js'

const style = {
  margin: 12,
};

class PackageQueryComponent extends React.Component {
  constructor(props) {
  		super(props);
  		this.state = {
          pkg_name: '',
          pkg_version: '',
          errorMessage: '',
  		};
      this.handleClick = this.handleClick.bind(this);
      this.handleReset = this.handleReset.bind(this);
  }

  componentDidMount() {
  //		client({method: 'GET', path: '/api/employees'}).done(response => {
  //			this.setState({employees: response.entity._embedded.employees});
  //		});
  }
  componentWillReceiveProps(nextProps) {
    console.log("componentWillReceiveProps");
    console.log(nextProps);
    if(nextProps!=null) {
      this.setState({
        pkg_name: nextProps.pkg_name,
        pkg_version: nextProps.pkg_version,
        errorMessage: nextProps.errorMessage
      });
    }
  }
  handleClick() {
    console.log("Search clicked")
    console.log(this.state.pkg_name)
    this.props.requestPackageSearchAction(this.state.pkg_name, this.state.pkg_version)
  }
  handleReset() {
    this.props.resetPackageSearchFormAction()
  }
  renderErrorMessage() {
    const { errorMessage } = this.props
    if (!errorMessage) {
      return null
    }

    return (
      <p style={{ backgroundColor: '#e99', padding: 10 }}>
        <b>{errorMessage}</b>
        {' '}
      </p>
    )
  }
  render() {
    return (
      <div style={style}>
          <TextField
            hintText="Package Name"
            floatingLabelText="Name"
            floatingLabelFixed={true}
            value={this.state.pkg_name}
            onChange={(e, newValue) => this.setState({ pkg_name: newValue})}
          />
          <TextField
            hintText="Package Version"
            floatingLabelText="Version"
            floatingLabelFixed={true}
            value={this.state.pkg_version}
            onChange={(e, newValue) => this.setState({ pkg_version: newValue})}
          />
        <RaisedButton label="Search" primary={true} style={style} onClick={this.handleClick}/>
        <RaisedButton label="Clear" primary={false} style={style} onClick={this.handleReset}/>
        {this.renderErrorMessage()}
      </div>

    );
  }
}

PackageQueryComponent.propTypes = {
  // Injected by React Redux
  errorMessage: PropTypes.string,
  //resetErrorMessage: PropTypes.func.isRequired,
  pkg_name: PropTypes.string,
  pkg_version: PropTypes.string,
}

function mapStateToProps (state, ownProps) {
  console.log("mapping state to props ");
  // this is the redux stat not this.state, combineReducers
  console.log(state.pkgqueryformReducer);
  return {
    errorMessage: state.pkgqueryformReducer.errorMessage,
    pkg_name: state.pkgqueryformReducer.pkg_name,
    pkg_version: state.pkgqueryformReducer.pkg_version
  }
}

function mapDispatchToProps(dispatch) {
  console.log("mapping dispatch to props");
  return {
   resetPackageSearchFormAction: bindActionCreators(resetPackageSearchForm, dispatch),
   requestPackageSearchAction: bindActionCreators(requestPackageSearch, dispatch)
  }
}

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(PackageQueryComponent);
