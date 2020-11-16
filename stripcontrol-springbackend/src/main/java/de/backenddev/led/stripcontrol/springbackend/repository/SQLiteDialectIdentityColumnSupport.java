package de.backenddev.led.stripcontrol.springbackend.repository;

import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.identity.GetGeneratedKeysDelegate;
import org.hibernate.dialect.identity.IdentityColumnSupport;
import org.hibernate.id.PostInsertIdentityPersister;

public class SQLiteDialectIdentityColumnSupport implements IdentityColumnSupport
{

	@Override
	public boolean supportsIdentityColumns( )
	{
		return true;
	}

	@Override
	public boolean hasDataTypeInIdentityColumn( )
	{
		return false;
	}

	@Override
	public String getIdentitySelectString( final String table, final String column, final int type )
	{
		return "select last_insert_rowid()";
	}

	@Override
	public String getIdentityColumnString( final int type )
	{
		// return "integer primary key autoincrement";
		// FIXME "autoincrement"
		return "integer";
	}

	@Override
	public boolean supportsInsertSelectIdentity( )
	{
		return true;
	}

	@Override
	public String appendIdentitySelectToInsert( final String insertString )
	{
		return null;
	}

	@Override
	public String getIdentityInsertString( )
	{
		return null;
	}

	@Override
	public GetGeneratedKeysDelegate buildGetGeneratedKeysDelegate( final PostInsertIdentityPersister persister,
			final Dialect dialect )
	{
		return null;
	}

}
