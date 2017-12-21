package dataAccess;

import java.util.concurrent.ConcurrentMap;

import org.mapdb.DB;

import entity.FriendsEntity;

public class FriendsDA {
	
	private static DB db;
	private static ConcurrentMap<String, FriendsEntity> friends;
	
}
