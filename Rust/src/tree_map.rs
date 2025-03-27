#![allow(dead_code)]

// Helper Module
// =============

mod node {

	pub struct Node<K, V> {
		pub key: K,
		pub value: V,
		pub left: Option<Box<Node<K, V>>>,
		pub right: Option<Box<Node<K, V>>>,
	}

}


// Main Module
// ===========
use node::*;


pub struct TreeMap<K, V> {
	count: usize,
	root: Option<Box<Node<K, V>>>,
}

impl<K, V> TreeMap<K, V> {

    pub fn new() -> TreeMap<K, V> {
    	TreeMap { count: 0, root: None }
    }

    pub fn len(&self) -> usize {
    	self.count
    	// If no root then 0 else root.len()
    	// Offload the recursion to the nodes.
    }

    pub fn insert(&mut self, pair: (K, V)) {
    	let new_node = Box::new(Node {
    		key: pair.0,
    		value: pair.1,
    		left: None,
    		right: None,
    	});

    	fn insert_node<K, V>(current: &Box<Node<K, V>>, new_node: Box<Node<K, V>>) {

    	}

        if let Some(node_ptr) = &self.root {
    		insert_node(node_ptr, new_node);
        }
        else {
        	self.root = Some(new_node);
        	self.count = 1;
        }
    }

}

#[cfg(test)]
mod tests {
	use super::*;

    #[test]
    fn construction_works() {

	    let my_tree: TreeMap<String, i32> = TreeMap::new();
	    assert_eq!(my_tree.count, 0);
	    assert!(my_tree.root.is_none());
    }

}

