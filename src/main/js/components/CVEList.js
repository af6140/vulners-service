import React from 'react';
import Paper from 'material-ui/Paper';
import {Card, CardActions, CardHeader, CardText, CardTitle} from 'material-ui/Card';
import Chip from 'material-ui/Chip';

const ReactMarkdown = require('react-markdown')

const paperStyle = {
  width: '80%',
  margin: 20,
  textAlign: 'left',
  display: 'inline-block',
};

const RefLinks = ({references}) => {
  var links = references.map((link,idx) => {
    var k = `references_p${idx}`;
    return <li key={k}><a href={link} target="_blank">{link}</a></li>
  });
  return <ul>{links}</ul>;
}

const CVEDescription = ({text}) => {
  return <ReactMarkdown source={text} />
}

const CVESummaryCard = ({cve}) => {
  var cveids = cve.cveList.map((cveid, idx) => {
    var k = `cve_id${idx}`;
    return <span key={k}>{cveid}&nbsp;</span>;
  });
  var title = `${cve.id}`;
  return <Card>
    <CardTitle
      title={title}
      subtitle={cveids}
    />
    <CardText>
      <h3>Description:</h3>
      <CVEDescription text={cve.description} />
      <h3>References:</h3>
      <RefLinks references={cve.references} />
      <strong>Published:</strong> {cve.published}<br/ >
      <strong>Modified:</strong> {cve.modified}<br />
      <strong>Last Seen:</strong> {cve.lastSeen}<br />
      <p>
        <strong>CVSS Score:</strong>{cve.cvssScore}<br />
        <strong>CVSS Vector:</strong>{cve.cvssVector}
      </p>
    </CardText>

  </Card>
};
const CVElist = ({cves}) => {
  return (
   <div>
      {cves.map((cve, idx) => {
        var key = `cvelist_${idx}`;
        return <Paper key={key} style={paperStyle} zDepth={2}><CVESummaryCard key={key} cve={cve} /></Paper>
        }
      )}

   </div>
 );

}
export default CVElist;
