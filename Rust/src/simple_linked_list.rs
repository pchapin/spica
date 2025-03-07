#![allow(dead_code)]

// This sample code was created by Craig Damon (lightly edited by Peter Chapin).

use fmt::Display;
use std::fmt;

#[derive(Debug)]
pub struct LinkedList<T> {
    value: T,
    next: Option<Box<LinkedList<T>>>,
}

impl<T> LinkedList<T> {
    pub fn new(value: T) -> Box<Self> {
        Box::new(LinkedList { value, next: None })
    }

    pub fn head(&self) -> &T {
        &self.value
    }

    pub fn tail(&self) -> &Option<Box<Self>> {
        &self.next
    }

    pub fn get(&self, i: u32) -> Option<&T> {
        if i == 0 {
            return Some(&self.value);
        }
        if let Some(next) = &self.next {
            return next.get(i - 1);
        }
        None
    }

    pub fn is_empty(&self) -> bool {
        false
    }

    pub fn len(&self) -> u32 {
        if let Some(next) = &self.next {
            1 + next.len()
        } else {
            1
        }
    }

    pub fn add_end(&mut self, value: T) {
        if let Some(next) = &mut self.next {
            next.add_end(value)
        } else {
            self.next = Some(LinkedList::new(value))
        }
    }

    pub fn insert_first(self, value: T) -> Box<Self> {
        Box::new(LinkedList {
            value,
            next: Some(Box::new(self)),
        })
    }

    pub fn insert(mut list: Box<Self>, value: T, index: u32) -> Result<Box<Self>, String> {
        if index == 0 {
            Ok(list.insert_first(value))
        } else if let Some(next) = list.next {
            list.next = Some(LinkedList::insert(next, value, index - 1)?);
            Ok(list)
        } else if index == 1 {
            list.next = Some(LinkedList::new(value));
            Ok(list)
        } else {
            Err(String::from("out of bounds in list insert"))
        }
    }

    pub fn remove_first(self) -> (T, Option<Box<LinkedList<T>>>) {
        (self.value, self.next)
    }

    pub fn remove(mut node: Box<Self>, index: u32) -> Result<Option<Box<Self>>, String> {
        if index == 0 {
            Ok(node.next)
        } else if let Some(next) = node.next {
            node.next = LinkedList::remove(next, index - 1)?;
            Ok(Some(node))
        } else {
            Err(String::from("out of bounds in list remove"))
        }
    }
}

impl<T> LinkedList<T>
where
    T: PartialEq<T>,
{
    pub fn index_of(&self, value: &T) -> Option<u32> {
        if &self.value == value {
            Some(0)
        } else if let Some(next) = &self.next {
            next.index_of(value).map(|x| x + 1)
        } else {
            None
        }
    }

    pub fn remove_value(mut node: Box<Self>, value: T) -> Result<Option<Box<Self>>, String> {
        if &node.value == &value {
            Ok(node.next)
        } else if let Some(next) = node.next {
            node.next = LinkedList::remove_value(next, value)?;
            Ok(Some(node))
        } else {
            Err(String::from("could not find value to remove"))
        }
    }
}

impl<T: PartialEq> PartialEq for LinkedList<T> {
    fn eq(&self, other: &Self) -> bool {
        if self.value == other.value {
            if let Some(next) = &self.next {
                if let Some(onext) = &other.next {
                    next == onext
                } else {
                    false
                }
            } else {
                other.next.is_none()
            }
        } else {
            false
        }
    }
}

impl<T: Display> Display for LinkedList<T> {
    fn fmt(&self, f: &mut fmt::Formatter<'_>) -> fmt::Result {
        write!(f, "[{}", self.value)?;
        let mut link = self;
        while let Some(ll) = &link.next {
            write!(f, ", {}", ll.value)?;
            link = ll;
        }
        write!(f, "]")
    }
}

// impl LinkedList<T> {
//     pub fn<'a> iter(&'a self)->RefIter<'a,T> {
//         RefIter::new(Some(self))
//     }
//     pub fn<'a> iter_mut(&'a mut self)->MutIter<'a,T> {
//         MutIter::new(Some(self))
//     }
// }

pub struct RefIter<'a, T: 'a> {
    next: Option<&'a Box<LinkedList<T>>>,
}

impl<'a, T> RefIter<'a, T> {
    pub fn new(first_node: &'a Box<LinkedList<T>>) -> Self {
        RefIter {
            next: Some(first_node),
        }
    }
}

impl<'a, T: 'a> Iterator for RefIter<'a, T> {
    type Item = &'a T;
    fn next(&mut self) -> Option<&'a T> {
        if let Some(next) = self.next {
            self.next = if let Some(new_next) = &next.next {
                Some(new_next)
            } else {
                None
            };
            Some(&next.value)
        } else {
            None
        }
    }
}

// pub struct MutIter<'a, T: 'a> {
//     next: Option<&'a mut Box<LinkedList<T>>>,
// }

// impl<'a, T> MutIter<'a, T> {
//     pub fn new(first_node: &'a mut Box<LinkedList<T>>) -> Self {
//         MutIter{next:Some(first_node)}
//     }
// }

// impl<'a,T> LinkedList<T> {
//     fn split_mut(&'a mut self)->(&'a mut T,Option<&'a mut Box<LinkedList<T>>>)
//        { (&mut self.value,(&mut self.next).as_mut()) }
// }

// impl<'a,T:'a> Iterator for MutIter<'a,T> {
//     type Item = &'a mut T;
//     fn next(&mut self)->Option<&'a mut T> {
//         if self.next.is_none() { None}
//         else {
//         let (tv,nxt) = self.next.as_mut().unwrap().split_mut();
//         self.next = nxt;
//         Some(tv)
//    }
//  if let Some(next) =  self.next.as_mut() {
//     self.next = if let Some(new_next) = &mut next.next { Some(new_next) }
//  }
//  if let Some(next) =  self.next.as_mut() {

//     Some(&mut next.value)
// }
// else { None }
//    }
//}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn get_works() {
        let mut ll = LinkedList::new(1);
        assert_eq!(ll.get(0), Some(&1));
        assert_eq!(ll.get(1), None);
        ll.add_end(2);
        assert_eq!(ll.get(0), Some(&1));
        assert_eq!(ll.get(1), Some(&2));
        assert_eq!(ll.get(2), None);
    }

    #[test]
    fn format_works() {
        let mut ll = LinkedList::new(1);
        assert_eq!(format!("{ll}"), "[1]");
        ll.add_end(2);
        assert_eq!(format!("{ll}"), "[1,2]");
    }

    #[test]
    fn len_works() {
        let mut ll = LinkedList::new(1);
        assert_eq!(ll.len(), 1);
        ll.add_end(2);
        assert_eq!(ll.len(), 2);
    }

    #[test]
    fn index_works() {
        let mut ll = LinkedList::new(1);
        ll.add_end(2);
        assert_eq!(ll.index_of(&1), Some(0));
        assert_eq!(ll.index_of(&2), Some(1));
        assert_eq!(ll.index_of(&3), None);
    }

    #[test]
    fn remove_first_works() {
        let mut ll = LinkedList::new(1);
        ll.add_end(2);
        let (val, ll_opt) = ll.remove_first();
        assert_eq!(val, 1);
        assert!(ll_opt.is_some());
        let ll = ll_opt.unwrap();
        assert_eq!(ll.len(), 1);
        let (val, ll_opt) = ll.remove_first();
        assert_eq!(val, 2);
        assert!(ll_opt.is_none());
    }

    #[test]
    fn remove_works() {
        let mut ll = LinkedList::new(1);
        ll.add_end(2);
        let ll_opt = LinkedList::remove(ll, 1).unwrap();
        assert!(ll_opt.is_some());
        let ll = ll_opt.unwrap();
        assert_eq!(ll.len(), 1);
        assert_eq!(ll.get(0), Some(&1));
        assert_eq!(ll.get(1), None);
    }

    #[test]
    fn eq_works() {
        let mut ll1 = LinkedList::new(1);
        ll1.add_end(2);
        let mut ll2 = LinkedList::new(1);
        assert_ne!(ll1, ll2);
        ll2.add_end(2);
        assert_eq!(ll1, ll2);
        ll2.add_end(3);
        assert_ne!(ll1, ll2);
    }

    #[test]
    fn insert_first_works() {
        let ll = LinkedList::new(1);
        let ll = ll.insert_first(2);
        assert_eq!(ll.len(), 2);
        assert_eq!(ll.get(0), Some(&2));
        assert_eq!(ll.get(1), Some(&1));
        assert_eq!(ll.get(2), None);
    }

    #[test]
    fn insert_works() {
        let ll = LinkedList::new(1);
        let ll = LinkedList::insert(ll, 2, 1).unwrap();
        assert_eq!(ll.len(), 2);
        assert_eq!(ll.get(0), Some(&1));
        assert_eq!(ll.get(1), Some(&2));
        assert_eq!(ll.get(2), None);
        let ll = LinkedList::insert(ll, 3, 1).unwrap();
        assert_eq!(ll.len(), 3);
        assert_eq!(ll.get(0), Some(&1));
        assert_eq!(ll.get(1), Some(&3));
        assert_eq!(ll.get(2), Some(&2));
        assert_eq!(ll.get(3), None);
    }

    #[test]
    fn iter_works() {
        let ll = LinkedList::new(1);
        let ll = LinkedList::insert(ll, 2, 1).unwrap();
        let mut iter = RefIter::new(&ll);
        assert_eq!(iter.next(), Some(&1));
        assert_eq!(iter.next(), Some(&2));
        assert_eq!(iter.next(), None);
    }
}
